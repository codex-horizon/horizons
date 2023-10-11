package com.later.horizon.core.security.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationFailureHandler implements org.springframework.security.web.authentication.AuthenticationFailureHandler {

    private static final Logger Logger = LoggerFactory.getLogger(AuthenticationFailureHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        if (Logger.isDebugEnabled()) {
            Logger.debug("in AuthenticationFailureHandler onAuthenticationFailure.");
        }
    }

}
