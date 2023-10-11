package com.later.horizon.core.security.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class UsernamePasswordAuthenticationFilter extends org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter {

    private static final Logger Logger = LoggerFactory.getLogger(UsernamePasswordAuthenticationFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (Logger.isDebugEnabled()) {
            String username = servletRequest.getParameter(super.getUsernameParameter());
            String password = servletRequest.getParameter(super.getPasswordParameter());
            Logger.debug("in UsernamePasswordAuthenticationFilter doFilter:{},{}.", username, password);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

}
