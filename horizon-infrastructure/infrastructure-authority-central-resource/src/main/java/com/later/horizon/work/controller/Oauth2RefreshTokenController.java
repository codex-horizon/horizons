package com.later.horizon.work.controller;

import com.later.horizon.work.service.IOauth2RefreshTokenService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth2RefreshToken")
public class Oauth2RefreshTokenController {

    private final IOauth2RefreshTokenService iOauth2RefreshTokenService;

    Oauth2RefreshTokenController(final IOauth2RefreshTokenService iOauth2RefreshTokenService) {
        this.iOauth2RefreshTokenService = iOauth2RefreshTokenService;
    }
}
