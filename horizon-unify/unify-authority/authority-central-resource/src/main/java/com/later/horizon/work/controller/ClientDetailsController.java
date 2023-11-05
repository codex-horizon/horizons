package com.later.horizon.work.controller;

import com.later.horizon.common.restful.response.ApiResult;
import com.later.horizon.common.restful.response.IResult;
import com.later.horizon.work.service.IClientDetailsService;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clientDetails")
public class ClientDetailsController {

    private final IClientDetailsService iClientDetailsService;

    ClientDetailsController(final IClientDetailsService iClientDetailsService) {
        this.iClientDetailsService = iClientDetailsService;
    }

    @RequestMapping(name = "新增客户端", path = {"/add", "/init"}, method = RequestMethod.POST)
    public IResult<String> add(@RequestBody BaseClientDetails baseClientDetails) {
        iClientDetailsService.add(baseClientDetails);
        return ApiResult.succeeded();
    }

}
