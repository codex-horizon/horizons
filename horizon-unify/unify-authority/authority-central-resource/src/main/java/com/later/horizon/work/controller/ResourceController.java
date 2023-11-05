package com.later.horizon.work.controller;

import com.later.horizon.common.restful.response.ApiResult;
import com.later.horizon.common.restful.response.IResult;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/resource")
public class ResourceController {

    /**
     * 匿名访问
     */
    @GetMapping("/public/{id}")
    public IResult<String> publicResource(@PathVariable long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication instanceof AnonymousAuthenticationToken ? ApiResult.succeeded("No User：" + "this is public " + id) : ApiResult.succeeded(authentication.getName() + "：" + "this is public " + id);
    }

    /**
     * 受限访问
     */
    @GetMapping("/protect/{id}")
    public IResult<String> protectResource(@PathVariable long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication instanceof OAuth2Authentication ? ApiResult.succeeded(authentication.getName() + "：" + "this is protect " + id) : ApiResult.succeeded("No User：" + "this is protect " + id);
    }

    /**
     * 受限访问，用户信息
     */
    @GetMapping("/protect/user")
    public IResult<Principal> user(Principal principal) {
        return ApiResult.succeeded(principal);
    }

}
