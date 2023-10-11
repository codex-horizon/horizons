package com.later.horizon.core.security.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutHandler implements org.springframework.security.web.authentication.logout.LogoutHandler {

    private static final Logger Logger = LoggerFactory.getLogger(LogoutHandler.class);

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (Logger.isDebugEnabled()) {
            Logger.debug("in LogoutHandler logout.");
        }
    }

}
