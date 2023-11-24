package com.later.horizon.core.interceptor;

import com.later.horizon.common.helper.RequestHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.User;
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

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry
                .addInterceptor(new WebMvcInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns();
    }

    @Slf4j
    static final class WebMvcInterceptor implements HandlerInterceptor {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
            HttpSession httpSession = RequestHelper.getHttpSession(Boolean.TRUE);
            User user = new User("test", "test", AuthorityUtils.NO_AUTHORITIES);
            TestingAuthenticationToken testingAuthenticationToken = new TestingAuthenticationToken(user, httpSession.getId());
            testingAuthenticationToken.setAuthenticated(Boolean.TRUE);
            SecurityContext securityContext = new SecurityContextImpl(testingAuthenticationToken);
            httpSession.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
            SecurityContextHolder.setContext(securityContext);
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


