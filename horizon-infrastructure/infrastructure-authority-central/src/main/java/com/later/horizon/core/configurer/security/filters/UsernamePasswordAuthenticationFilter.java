package com.later.horizon.core.configurer.security.filters;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

@Slf4j
public class UsernamePasswordAuthenticationFilter extends org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String username = servletRequest.getParameter(super.getUsernameParameter());
        String password = servletRequest.getParameter(super.getPasswordParameter());
        log.trace("in UsernamePasswordAuthenticationFilter doFilter:{},{}.", username, password);
        filterChain.doFilter(servletRequest, servletResponse);
    }

}
