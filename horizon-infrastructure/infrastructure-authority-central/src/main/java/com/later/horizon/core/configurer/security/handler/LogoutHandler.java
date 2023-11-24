package com.later.horizon.core.configurer.security.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class LogoutHandler implements org.springframework.security.web.authentication.logout.LogoutHandler {

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        log.trace("in LogoutHandler logout.");
    }

}
