package com.later.horizon.work.controller;

import com.later.horizon.common.restful.IResult;
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
    IResult<String> publicResource(@PathVariable long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication instanceof AnonymousAuthenticationToken ? IResult.Result.succeeded("No User：" + "this is public " + id) : IResult.Result.succeeded(authentication.getName() + "：" + "this is public " + id);
    }

    /**
     * 受限访问
     */
    @GetMapping("/protect/{id}")
    IResult<String> protectResource(@PathVariable long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication instanceof OAuth2Authentication ? IResult.Result.succeeded(authentication.getName() + "：" + "this is protect " + id) : IResult.Result.succeeded("No User：" + "this is protect " + id);
    }

    /**
     * 受限访问，用户信息
     */
    @GetMapping("/protect/user")
    IResult<Principal> user(Principal principal) {
        return IResult.Result.succeeded(principal);
    }

}
