package com.later.horizon.core.security.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationSuccessHandler implements org.springframework.security.web.authentication.AuthenticationSuccessHandler {

    private static final Logger Logger = LoggerFactory.getLogger(AuthenticationSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        if (Logger.isDebugEnabled()) {
            Logger.debug("in AuthenticationSuccessHandler onAuthenticationSuccess");
        }
        chain.doFilter(request, response);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        if (Logger.isDebugEnabled()) {
            Logger.debug("in AuthenticationSuccessHandler onAuthenticationSuccess.");
        }
    }

}
