package com.later.horizon.core.filters;

import com.later.horizon.common.constants.Constants;
import com.later.horizon.common.helper.RequestHelper;
import com.later.horizon.core.configurer.CommonConfigurer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.cors.CorsUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class TraceFilter implements Filter {

    private final CommonConfigurer commonConfigurer;

    public TraceFilter(final CommonConfigurer commonConfigurer) {
        this.commonConfigurer = commonConfigurer;
    }

    @Override
    public void init(FilterConfig filterConfig) {
        log.info("CommonFilter init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader(Constants.Header_Key_Application_Name, commonConfigurer.getApplicationName());
        response.setHeader(Constants.Header_Trace_Id, RequestHelper.createTraceId());
        if (CorsUtils.isCorsRequest(request)) {
            String[] corsHeaders = {HttpHeaders.CONTENT_TYPE};
            response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, request.getHeader(HttpHeaders.ORIGIN));
            response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, String.join(",", corsHeaders));
            response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, request.getMethod());
            response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, Boolean.TRUE.toString());
            response.setHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, Integer.toString(60 * 60 * 2));
        }
//        if (!CorsUtils.isPreFlightRequest(request)) {
//            filterChain.doFilter(request, response);
//        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        log.info("CommonFilter destroy");
    }

}
