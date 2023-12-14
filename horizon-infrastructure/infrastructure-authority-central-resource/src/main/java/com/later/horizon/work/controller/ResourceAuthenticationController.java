package com.later.horizon.work.controller;

import com.later.horizon.common.helper.CommonHelper;
import com.later.horizon.common.restful.IResult;
import com.later.horizon.core.configurer.CommonConfigurer;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class ResourceAuthenticationController {

    private final CommonConfigurer commonConfigurer;

    ResourceAuthenticationController(final CommonConfigurer commonConfigurer) {
        this.commonConfigurer = commonConfigurer;
    }

    @RequestMapping(name = "登录页", path = "/", method = RequestMethod.GET)
    String loginView() {
        return "redirect:" + commonConfigurer.getAuthorityCentralUrl() + "/oauth/authorize" +
                "?response_type=" + commonConfigurer.getAuthorityCentralClientAuthorizedGrantTypes()[0] +
                "&client_id=" + commonConfigurer.getAuthorityCentralClientId() +
                "&redirect_uri=" + commonConfigurer.getAuthorityCentralClientRedirectUrl() +
                "&scope=" + commonConfigurer.getAuthorityCentralClientScope() +
                "&state=" + CommonHelper.createUUID();
    }

    @RequestMapping(name = "授权服务器间接通过资源服务的重定向获取授权码", path = "/indirect/", method = RequestMethod.GET)
    String redirectView(@RequestParam("code") String code, @RequestParam("state") String state, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("code", code);
        redirectAttributes.addAttribute("state", state);
        return "redirect:" + commonConfigurer.getAuthorityCentralUrl();
    }

    /**
     * 匿名访问
     */
    @GetMapping("/resource/public/{id}")
    @ResponseBody
    IResult<String> publicResource(@PathVariable long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication instanceof AnonymousAuthenticationToken ? IResult.Result.succeeded("No User：" + "this is public " + id) : IResult.Result.succeeded(authentication.getName() + "：" + "this is public " + id);
    }

    /**
     * 受限访问
     */
    @GetMapping("/resource/protect/{id}")
    @ResponseBody
    IResult<String> protectResource(@PathVariable long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication instanceof OAuth2Authentication ? IResult.Result.succeeded(authentication.getName() + "：" + "this is protect " + id) : IResult.Result.succeeded("No User：" + "this is protect " + id);
    }

    /**
     * 受限访问，用户信息
     */
    @GetMapping("/resource/protect/user")
    @ResponseBody
    IResult<Principal> user(Principal principal) {
        return IResult.Result.succeeded(principal);
    }

}