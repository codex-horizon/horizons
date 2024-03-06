package com.later.horizon.module.controller;

import com.later.horizon.common.restful.IResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(name = "用户管理包括账户", path = "/user")
public class UserController {

    @GetMapping(name = "实验性", path = "/exp/{id}")
    public IResponse<Long> exp(@PathVariable("id") Long id) {
        return IResponse.Result.succeeded(id);
    }
}
