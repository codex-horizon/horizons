package com.later.horizon.work.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
public class AuthorityCentralResourceController {

    @RequestMapping(name = "登录页", path = "/", method = RequestMethod.GET)
    String loginView() {
        return "redirect:http://127.0.0.1:1469/infrastructureAuthorityCentral/oauth/authorize?response_type=code&client_id=0d55e4c5-aeb2-4e00-9b67-80c8f31918d8&redirect_uri=http://127.0.0.1:1649/infrastructureAuthorityCentralResource/indirect&scope=read&state=123456";
    }

    @RequestMapping(name = "授权服务器间接通过资源服务的重定向获取授权码", path = "/indirect", method = RequestMethod.GET)
    String redirectView(@RequestParam("code") String code, RedirectAttributes redirectAttributes) {
        log.info("------------------ in AuthorityCentralResourceController#redirectView 参数：{} ------------------", code);
        log.info("------------------ in AuthorityCentralResourceController#redirectView 参数：{} ------------------", code);
        log.info("------------------ in AuthorityCentralResourceController#redirectView 参数：{} ------------------", code);
        log.info("------------------ in AuthorityCentralResourceController#redirectView 参数：{} ------------------", code);
        log.info("------------------ in AuthorityCentralResourceController#redirectView 参数：{} ------------------ \n", code);
        redirectAttributes.addAttribute("code", code);
        return "redirect:http://127.0.0.1:1469/infrastructureAuthorityCentral/";
    }

}
