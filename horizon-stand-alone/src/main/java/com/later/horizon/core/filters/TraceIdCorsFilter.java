package com.later.horizon.core.filters;

import com.later.horizon.common.constants.Constants;
import com.later.horizon.common.helper.EncryptAESHelper;
import com.later.horizon.common.helper.CommonHelper;
import com.later.horizon.core.configurer.ValuesConfigurer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class TraceIdCorsFilter extends OncePerRequestFilter {

    private final ValuesConfigurer valuesConfigurer;

    public TraceIdCorsFilter(final ValuesConfigurer valuesConfigurer) {
        this.valuesConfigurer = valuesConfigurer;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
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
        String headerUrlEncipher = request.getHeader(Constants.Header_URL_Encipher);
        if (Boolean.parseBoolean(headerUrlEncipher)) {
            String encryptedURI = request.getRequestURI().substring(request.getContextPath().length() + 1);
            String decryptedURI = EncryptAESHelper.decrypt(encryptedURI, EncryptAESHelper.initSecretKey(valuesConfigurer.getPasswordSeedRequestUrl()));
            request.getRequestDispatcher(decryptedURI).forward(request, response);
            return;
        }
        if (valuesConfigurer.getEncipherRequestUrl()) {
            String encryptedURI = request.getRequestURI().substring(request.getContextPath().length() + 1);
            String decryptedURI = EncryptAESHelper.decrypt(encryptedURI, EncryptAESHelper.initSecretKey(valuesConfigurer.getPasswordSeedRequestUrl()));
            request.getRequestDispatcher(decryptedURI).forward(request, response);
            return;
        }
        filterChain.doFilter(request, response);
    }
}
