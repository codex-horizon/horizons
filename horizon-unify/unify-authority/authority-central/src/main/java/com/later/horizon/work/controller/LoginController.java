package com.later.horizon.work.controller;

import com.later.horizon.work.service.IUserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    private final IUserDetailsService iUserDetailsService;

    LoginController(final IUserDetailsService iUserDetailsService) {
        this.iUserDetailsService = iUserDetailsService;
    }

    @RequestMapping(name = "登录", path = "/login_view", method = RequestMethod.GET)
    public String loginView() {
        return "login_view";
    }

    @RequestMapping(name = "登录成功", path = "/login_succeed_view", method = RequestMethod.GET)
    public ModelAndView loginSucceedView(ModelAndView modelAndView) {
        modelAndView.setViewName("login_succeed_view");
        return modelAndView;
    }

    @RequestMapping(name = "登录失败", path = "/login_failed_view", method = RequestMethod.GET)
    public ModelAndView loginFailedView(ModelAndView modelAndView) {
        modelAndView.setViewName("login_failed_view");
        return modelAndView;
    }

    @RequestMapping(name = "登出", path = "/logout_view", method = RequestMethod.GET)
    public ModelAndView logoutView(ModelAndView modelAndView) {
        modelAndView.setViewName("logout_view");
        return modelAndView;
    }
}
