package com.later.horizon.core.interceptor;

import com.later.horizon.common.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Configuration
public class WebMvcInterceptorRegister implements WebMvcConfigurer {

    @Value("${spring.profiles.active}")
    private String springProfilesActive;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry
                .addInterceptor(new WebMvcInterceptor(springProfilesActive))
                .addPathPatterns("/**")
                .excludePathPatterns();
    }

    @Slf4j
    static final class WebMvcInterceptor implements HandlerInterceptor {

        private final String springProfilesActive;

        WebMvcInterceptor(final String springProfilesActive) {
            this.springProfilesActive = springProfilesActive;
        }

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
            if (Constants.Default_Spring_Profiles_Active.equals(springProfilesActive)) {
                HttpSession httpSession = request.getSession();
                UserDetails userDetails = new User(Constants.Default_Authentication_Username, Constants.Default_Authentication_Password, AuthorityUtils.NO_AUTHORITIES);
                TestingAuthenticationToken authenticationToken = new TestingAuthenticationToken(userDetails, httpSession.getId());
                authenticationToken.setAuthenticated(Boolean.TRUE);
                SecurityContext securityContext = new SecurityContextImpl(authenticationToken);
                httpSession.setAttribute(Constants.Session_Spring_Security_Context, securityContext);
                SecurityContextHolder.setContext(securityContext);
            }
            return Boolean.TRUE;
        }

        @Override
        public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        }

        @Override
        public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        }

    }

    /**
     * 注意：自定义过滤器后，该处跨域配置则失效
     *
     * @param registry 注册
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowedOriginPatterns("*")
                .allowCredentials(true);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

    }
}


