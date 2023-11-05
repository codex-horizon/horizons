package com.later.horizon.work.controller;

import com.later.horizon.common.helper.CaptchaHelper;
import com.later.horizon.common.restful.response.ApiResult;
import com.later.horizon.common.restful.response.IResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @RequestMapping(name = "获取验证码", path = "/fetchCaptcha/{uuid}", method = RequestMethod.POST)
    public IResult<String> fetchCaptcha(@PathVariable("uuid") String uuid){
        return ApiResult.succeeded(CaptchaHelper.create(uuid));
    }
}
