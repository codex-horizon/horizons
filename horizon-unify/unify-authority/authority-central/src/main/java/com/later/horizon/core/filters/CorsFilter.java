package com.later.horizon.core.filters;

import com.later.horizon.core.configurer.CommonConfigurer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.cors.CorsUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class CorsFilter implements Filter {

    private final CommonConfigurer commonConfigurer;

    public CorsFilter(final CommonConfigurer commonConfigurer) {
        this.commonConfigurer = commonConfigurer;
    }

    @Override
    public void init(FilterConfig filterConfig) {
        log.info("in CorsFilter init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (CorsUtils.isCorsRequest(request)) {
            String[] corsHeaders = {HttpHeaders.CONTENT_TYPE};
            response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, request.getHeader(HttpHeaders.ORIGIN));
            response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, String.join(",", corsHeaders));
            response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, request.getMethod());
            response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, Boolean.TRUE.toString());
            response.setHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, Integer.toString(60 * 60 * 2));
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        log.info("in CorsFilter destroy");
    }

}
