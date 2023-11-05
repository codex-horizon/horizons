package com.later.horizon.work.controller;

import com.later.horizon.common.restful.response.ApiResult;
import com.later.horizon.common.restful.response.IResult;
import com.later.horizon.work.service.IClientDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clientDetails")
public class ClientDetailsController {

    private final IClientDetailsService iClientDetailsService;

    private final PasswordEncoder passwordEncoder;

    ClientDetailsController(final IClientDetailsService iClientDetailsService,
                            final PasswordEncoder passwordEncoder) {
        this.iClientDetailsService = iClientDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * {
     * "client_id": "clientId",
     * "resource_ids": "1,2",
     * "client_secret": "{bcrypt}$2a$10$HzyBc4FlhXPkO25TepKk9ue1zEZ4oL4GUEKYn.5w55GDkZ4pU/hSK",
     * "scope": "READ,WRITE",
     * "authorized_grant_types": "authorization_code,password,refresh_token,implicit,client_credentials",
     * "redirect_uri": "https://juejin.cn/",
     * "authorities": "ALL,PROVIDED",
     * "access_token_validity": 3600,
     * "refresh_token_validity": 7200,
     * "additionalInformation": {
     * "param1": "value1"
     * },
     * "autoapprove": "true"
     * }
     */
    @RequestMapping(name = "新增客户端", path = {"/add", "/init"}, method = RequestMethod.POST)
    public IResult<String> add(@RequestBody BaseClientDetails clientDetails) {
        clientDetails.setClientSecret(passwordEncoder.encode(clientDetails.getClientSecret()));
        iClientDetailsService.add(clientDetails);
        return ApiResult.succeeded();
    }

}
