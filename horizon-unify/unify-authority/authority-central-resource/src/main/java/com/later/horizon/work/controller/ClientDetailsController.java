package com.later.horizon.work.controller;

import com.alibaba.fastjson2.JSONObject;
import com.later.horizon.common.restful.response.ApiResult;
import com.later.horizon.common.restful.response.IResult;
import com.later.horizon.work.service.IClientDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.util.ObjectUtils;
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

    @RequestMapping(name = "新增客户端", path = "/init", method = RequestMethod.GET)
    public IResult<String> init() {
        BaseClientDetails baseClientDetails = JSONObject.parseObject("{\"client_id\":\"0d55e4c5-aeb2-4e00-9b67-80c8f31918d8\",\"resource_ids\":\"r1,r2\",\"client_secret\":\"123456\",\"scope\":\"READ,WRITE\",\"authorized_grant_types\":\"authorization_code,password,refresh_token,implicit,client_credentials\",\"redirect_uri\":\"https://juejin.cn/\",\"authorities\":\"ALL,PROVIDED\",\"access_token_validity\":3600,\"refresh_token_validity\":7200,\"additionalInformation\":{\"k\":\"y\"},\"autoapprove\":\"true\"}", BaseClientDetails.class);
        ClientDetails clientDetails = iClientDetailsService.load(baseClientDetails.getClientId());
        if (ObjectUtils.isEmpty(clientDetails)) {
            baseClientDetails.setClientSecret(passwordEncoder.encode(baseClientDetails.getClientSecret()));
            iClientDetailsService.add(clientDetails);
        }
        return ApiResult.succeeded();
    }

}
