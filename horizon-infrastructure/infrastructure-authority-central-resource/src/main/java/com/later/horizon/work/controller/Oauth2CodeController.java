package com.later.horizon.work.controller;

import com.later.horizon.work.service.IOauth2CodeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth2Code")
public class Oauth2CodeController {

    private final IOauth2CodeService iOauth2CodeService;

    Oauth2CodeController(final IOauth2CodeService iOauth2CodeService) {
        this.iOauth2CodeService = iOauth2CodeService;
    }
}
