package com.later.horizon.core.filters;

import com.later.horizon.common.constants.Constants;
import com.later.horizon.common.helper.RequestHelper;
import com.later.horizon.core.configurer.CommonConfigurer;
import lombok.extern.slf4j.Slf4j;

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
        log.trace("in TraceFilter init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader(Constants.Header_Key_Application_Name, commonConfigurer.getApplicationName());
        response.setHeader(Constants.Header_Trace_Id, RequestHelper.createTraceId());
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        log.trace("in TraceFilter destroy");
    }

}
