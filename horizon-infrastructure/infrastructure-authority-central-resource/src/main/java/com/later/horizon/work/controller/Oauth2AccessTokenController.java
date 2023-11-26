package com.later.horizon.work.controller;

import com.later.horizon.work.service.IOauth2AccessTokenService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth2AccessToken")
public class Oauth2AccessTokenController {

    private final IOauth2AccessTokenService iOauth2AccessTokenService;

    Oauth2AccessTokenController(final IOauth2AccessTokenService iOauth2AccessTokenService) {
        this.iOauth2AccessTokenService = iOauth2AccessTokenService;
    }


}
