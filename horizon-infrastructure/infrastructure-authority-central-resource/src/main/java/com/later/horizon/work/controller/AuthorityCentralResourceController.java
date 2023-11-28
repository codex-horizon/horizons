package com.later.horizon.work.controller;

import com.later.horizon.common.helper.CommonHelper;
import com.later.horizon.core.configurer.CommonConfigurer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
public class AuthorityCentralResourceController {

    private final CommonConfigurer commonConfigurer;

    AuthorityCentralResourceController(final CommonConfigurer commonConfigurer) {
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

    @RequestMapping(name = "授权服务器间接通过资源服务的重定向获取授权码", path = "/indirect", method = RequestMethod.GET)
    String redirectView(@RequestParam("code") String code, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("code", code);
        return "redirect:" + commonConfigurer.getAuthorityCentralUrl();
    }

}
