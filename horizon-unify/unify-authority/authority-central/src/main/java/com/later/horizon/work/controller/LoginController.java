package com.later.horizon.work.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

    @RequestMapping(name = "登录页", path = "/login_view", method = RequestMethod.GET)
    public String loginView() {
        return "login_view";
    }

    @RequestMapping(name = "成功页", path = "/login_succeed_view", method = RequestMethod.GET)
    public String loginSucceedView() {
        return "login_succeed_view";
    }

    @RequestMapping(name = "失败页", path = "/login_failed_view", method = RequestMethod.GET)
    public String loginFailedView() {
        return "login_failed_view";
    }

    @RequestMapping(name = "主页", path = "/index_view", method = RequestMethod.GET)
    public String indexView() {
        return "index_view";
    }

    @RequestMapping(name = "清除", path = "/clear", method = RequestMethod.GET)
    public String clear() {
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            SecurityContextHolder.clearContext();
        }
        // 此处使用重定向可改变浏览器URL，较美观；
        return "redirect:login_view";
    }

}
