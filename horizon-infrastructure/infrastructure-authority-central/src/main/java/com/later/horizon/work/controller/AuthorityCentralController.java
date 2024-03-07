package com.later.horizon.work.controller;

import com.later.horizon.common.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@SuppressWarnings(Constants.Default_Suppress_Warnings_Deprecation)
@Slf4j
@Controller
public class AuthorityCentralController {

    private final ConsumerTokenServices consumerTokenServices;

    AuthorityCentralController(final ConsumerTokenServices consumerTokenServices) {
        this.consumerTokenServices = consumerTokenServices;
    }

    @RequestMapping(name = "登录页", path = "/login_view", method = RequestMethod.GET)
    String loginView(HttpServletRequest request, Model model) {
        CsrfToken csrfToken = CookieCsrfTokenRepository.withHttpOnlyFalse().loadToken(request);
        if (ObjectUtils.isEmpty(csrfToken)) {
            // 为何进入这一步，是因为 WebSecurityConfigurer 中配置的登出URL与请求类型一致的情况下，是无法进入本类的登出接口的；
            csrfToken = CookieCsrfTokenRepository.withHttpOnlyFalse().generateToken(request);
        }
        model.addAttribute(csrfToken.getParameterName(), csrfToken.getToken());
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
    String loginSucceedView(HttpSession session) {
        Object sessionSavedRequest = session.getAttribute(Constants.Default_Session_Spring_Security_Saved_Request);
        if (ObjectUtils.isEmpty(sessionSavedRequest)) {
            return "redirect:index_view";
        }
        SavedRequest savedRequest = (SavedRequest) sessionSavedRequest;
        return "redirect:" + savedRequest.getRedirectUrl();
    }

    /**
     * 当使用 Spring Security 使用【failureUrl】时，会以及GET方式进入该函数，进入到失败页（浏览器URL合规，美观）；
     * 当使用 Spring Security 使用【failureForwardUrl】时，会以及POST方式进入该函数，进入到失败页（浏览器URL合规）；
     *
     * @return 失败页
     */
    @RequestMapping(name = "失败页", path = "/login_failed_view", method = {RequestMethod.GET, RequestMethod.POST})
    String loginFailedView() {
        return "login_failed_view";
    }

    @RequestMapping(name = "主页", path = {"/", "/index_view"}, method = RequestMethod.GET)
    String indexView(HttpServletRequest request, Model model) {
        CsrfToken csrfToken = CookieCsrfTokenRepository.withHttpOnlyFalse().loadToken(request);
        model.addAttribute(csrfToken.getParameterName(), csrfToken.getToken());
        return "index_view";
    }

    /**
     * org.springframework.security.oauth2.provider.endpoint.WhitelabelApprovalEndpoint#getAccessConfirmation
     *
     * @param model                数据
     * @param request              请求
     * @param authorizationRequest 授权请求
     * @return 返回页面
     */
    @RequestMapping(name = "确认授权访问", path = "/oauth/confirm_access", method = RequestMethod.GET)
    String loginGrantView(HttpServletRequest request, Model model, @SessionAttribute("authorizationRequest") AuthorizationRequest authorizationRequest) {
        CsrfToken csrfToken = CookieCsrfTokenRepository.withHttpOnlyFalse().loadToken(request);
        model.addAttribute(csrfToken.getParameterName(), csrfToken.getToken());
        model.addAttribute("clientId", authorizationRequest.getClientId());
        model.addAttribute("scopes", authorizationRequest.getScope());
        return "login_grant_view";
    }

    /**
     * 当手动调用清除认证时，采用的是异步登出方式，那么前端调用接口后，必须location.reload(true)；
     *
     * @return 重定向登录页
     */
    @RequestMapping(name = "先吊销令牌，后清除认证", path = "/do_logout", method = RequestMethod.GET)
    String doLogout(@RequestHeader(name = Constants.Header_Access_Token, required = false) String accessToken, HttpSession session) {
        if (StringUtils.hasText(accessToken)) {
            consumerTokenServices.revokeToken(accessToken);
        }
        session.invalidate();
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            SecurityContextHolder.clearContext();
        }
        return "redirect:login_view";
    }
}
