package com.later.horizon.work.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @RequestMapping(name = "登录页", path = "/login_view", method = RequestMethod.GET)
    public String loginView() {
        return "login_view";
    }

    /**
     * 注意：【defaultSuccessUrl】与【successForwardUrl】二选其一，两者都用仅生效【defaultSuccessUrl】，（不论True与False：“不论指定页面未指定页面都进入【successForwardUrl】”）；
     * 当使用 Spring Security 使用【defaultSuccessUrl,Boolean】时，当True时，强制默认成功页；当False时，访问指定页面，用户未登入，跳转至登入页面，如果登入成功，（GET形式）跳转至用户访问指定页面，用户访问登入页面，默认的跳转页面；
     * 当使用 Spring Security 使用【successForwardUrl】时，会以及POST方式进入该函数，必须重定向到（认证okay）主页或其他页；
     *
     * @return 重定向主页或其他页
     */
    @RequestMapping(name = "成功页", path = "/login_succeed_view", method = {RequestMethod.GET, RequestMethod.POST})
    public String loginSucceedView() {
        return "redirect:index_view";
    }

    /**
     * 当使用 Spring Security 使用【failureUrl】时，会以及GET方式进入该函数，进入到失败页（浏览器URL合规，美观）；
     * 当使用 Spring Security 使用【failureForwardUrl】时，会以及POST方式进入该函数，进入到失败页（浏览器URL合规）；
     *
     * @return 失败页
     */
    @RequestMapping(name = "失败页", path = "/login_failed_view", method = {RequestMethod.GET, RequestMethod.POST})
    public String loginFailedView() {
        return "login_failed_view";
    }

    @RequestMapping(name = "主页", path = "/index_view", method = RequestMethod.GET)
    public String indexView() {
        return "index_view";
    }

    /**
     * 同时支持GET，POST。当手动调用清除认证时，推荐采用重定向可直观改变浏览器URL，较美观；
     *
     * @return 重定向登录页
     */
    @RequestMapping(name = "清除认证", path = "/do_logout", method = {RequestMethod.GET, RequestMethod.POST})
    public String doLogout(HttpServletRequest request) {
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            HttpSession session = request.getSession(false);
            request.getSession(false);
            session.invalidate();
            SecurityContextHolder.clearContext();
        }
        return "redirect:login_view";
    }

}
