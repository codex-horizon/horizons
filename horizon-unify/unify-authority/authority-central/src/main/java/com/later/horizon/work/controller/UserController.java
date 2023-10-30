package com.later.horizon.work.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    /**
     * http://127.0.0.1:1469/horizon/authority-central/login_view
     * @param modelAndView
     * @return
     */
    @RequestMapping(name = "登录", path = "/login_view", method = RequestMethod.GET)
    public ModelAndView loginView(ModelAndView modelAndView) {
        modelAndView.setViewName("login_view");
        return modelAndView;
    }
    @RequestMapping(name = "登录成功", path = "/login_succeed", method = RequestMethod.GET)
    public ModelAndView loginSucceed(ModelAndView modelAndView) {
        modelAndView.setViewName("login_succeed_view");
        return modelAndView;
    }
    @RequestMapping(name = "登录失败", path = "/login_failed", method = RequestMethod.GET)
    public ModelAndView loginFailed(ModelAndView modelAndView) {
        modelAndView.setViewName("login_failed_view");
        return modelAndView;
    }

    @RequestMapping(name = "登出", path = "/logout_view", method = RequestMethod.GET)
    public ModelAndView logoutView(ModelAndView modelAndView) {
        modelAndView.setViewName("logout_view");
        return modelAndView;
    }
}
