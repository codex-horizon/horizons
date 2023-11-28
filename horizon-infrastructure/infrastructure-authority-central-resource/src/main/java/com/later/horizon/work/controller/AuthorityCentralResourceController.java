package com.later.horizon.work.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthorityCentralResourceController {

    @RequestMapping(name = "授权服务器简介通过资源服务的重定向获取授权码", path = "/", method = RequestMethod.GET)
    String redirectView(@RequestParam("code") String code, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("code", code);
        return "redirect:http://127.0.0.1:1469/infrastructureAuthorityCentral/";
    }

}
