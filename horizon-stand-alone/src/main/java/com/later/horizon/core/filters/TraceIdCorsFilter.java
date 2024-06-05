package com.later.horizon.core.filters;

import com.later.horizon.common.constants.Constants;
import com.later.horizon.common.helper.EncryptAESHelper;
import com.later.horizon.common.helper.CommonHelper;
import com.later.horizon.core.configurer.ValuesConfigurer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.cors.CorsUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class TraceIdCorsFilter implements Filter {

    private final ValuesConfigurer valuesConfigurer;

    public TraceIdCorsFilter(final ValuesConfigurer valuesConfigurer) {
        this.valuesConfigurer = valuesConfigurer;
    }

    @Override
    public void init(FilterConfig filterConfig) {
        log.trace("in CorsFilter init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader(Constants.Header_Application_Name, valuesConfigurer.getApplicationName());
        response.setHeader(Constants.Header_Trace_Id, CommonHelper.createUUID());
        if (CorsUtils.isCorsRequest(request)) {
            String[] corsHeaders = {HttpHeaders.CONTENT_TYPE};
            response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, request.getHeader(HttpHeaders.ORIGIN));
            response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, String.join(",", corsHeaders));
            response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, request.getMethod());
            response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, Boolean.TRUE.toString());
            response.setHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, Long.toString(60 * 60 * 2));
        }
        // 预判URL是否加密（也可用Spring Security PasswordEncoder 替代）
        if (valuesConfigurer.getOpenRequestUrlEncipher()) {
            String encryptedURI = request.getRequestURI().substring(request.getContextPath().length() + 1);
            String decryptedURI = EncryptAESHelper.decrypt(encryptedURI, EncryptAESHelper.initSecretKey(valuesConfigurer.getRequestUrlEncipherPasswordSeed()));
            request.getRequestDispatcher(decryptedURI).forward(request, response);
            return;
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        log.trace("in CorsFilter destroy");
    }

}
