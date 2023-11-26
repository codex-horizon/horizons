package com.later.horizon.work.controller;

import com.later.horizon.work.service.IOauth2ClientTokenService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth2ClientToken")
public class Oauth2ClientTokenController {

    private final IOauth2ClientTokenService iOauth2ClientTokenService;

    Oauth2ClientTokenController(final IOauth2ClientTokenService iOauth2ClientTokenService) {
        this.iOauth2ClientTokenService = iOauth2ClientTokenService;
    }
}
