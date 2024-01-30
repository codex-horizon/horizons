package com.later.horizon.work.controller;

import com.later.horizon.common.helper.CommonHelper;
import com.later.horizon.common.restful.IResponse;
import com.later.horizon.core.configurer.ValuesConfigurer;
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

    private final ValuesConfigurer valuesConfigurer;

    ResourceAuthenticationController(final ValuesConfigurer valuesConfigurer) {
        this.valuesConfigurer = valuesConfigurer;
    }

    @RequestMapping(name = "登录页", path = "/", method = RequestMethod.GET)
    String loginView() {
        return "redirect:" + valuesConfigurer.getAuthorityCentralUrl() + "/oauth/authorize" +
                "?response_type=" + valuesConfigurer.getAuthorityCentralClientAuthorizedGrantTypes()[0] +
                "&client_id=" + valuesConfigurer.getAuthorityCentralClientId() +
                "&redirect_uri=" + valuesConfigurer.getAuthorityCentralClientRedirectUrl() +
                "&scope=" + valuesConfigurer.getAuthorityCentralClientScope() +
                "&state=" + CommonHelper.createUUID();
    }

    @RequestMapping(name = "授权服务器间接通过资源服务的重定向获取授权码", path = "/indirect/", method = RequestMethod.GET)
    String redirectView(@RequestParam("code") String code, @RequestParam("state") String state, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("code", code);
        redirectAttributes.addAttribute("state", state);
        return "redirect:" + valuesConfigurer.getAuthorityCentralUrl();
    }

    /**
     * 匿名访问
     */
    @GetMapping("/resource/public/{id}")
    @ResponseBody
    IResponse<String> publicResource(@PathVariable long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication instanceof AnonymousAuthenticationToken ? IResponse.Result.succeeded("No User：" + "this is public " + id) : IResponse.Result.succeeded(authentication.getName() + "：" + "this is public " + id);
    }

    /**
     * 受限访问
     */
    @GetMapping("/resource/protect/{id}")
    @ResponseBody
    IResponse<String> protectResource(@PathVariable long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication instanceof OAuth2Authentication ? IResponse.Result.succeeded(authentication.getName() + "：" + "this is protect " + id) : IResponse.Result.succeeded("No User：" + "this is protect " + id);
    }

    /**
     * 受限访问，用户信息
     */
    @GetMapping("/resource/protect/user")
    @ResponseBody
    IResponse<Principal> user(Principal principal) {
        return IResponse.Result.succeeded(principal);
    }

}