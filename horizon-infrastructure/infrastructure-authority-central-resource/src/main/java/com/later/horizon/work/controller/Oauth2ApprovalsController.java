package com.later.horizon.work.controller;

import com.later.horizon.work.service.IOauth2ApprovalsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth2Approvals")
public class Oauth2ApprovalsController {

    private final IOauth2ApprovalsService iOauth2ApprovalsService;

    Oauth2ApprovalsController(final IOauth2ApprovalsService iOauth2ApprovalsService) {
        this.iOauth2ApprovalsService = iOauth2ApprovalsService;
    }

}
