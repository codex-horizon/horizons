package com.later.horizon.core.interceptor;

import com.later.horizon.common.constants.Constants;
import com.later.horizon.core.configurer.ValuesConfigurer;
import lombok.extern.slf4j.Slf4j;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Configuration
public class WebMvcConfigurer implements org.springframework.web.servlet.config.annotation.WebMvcConfigurer {

    private final ValuesConfigurer valuesConfigurer;

    WebMvcConfigurer(final ValuesConfigurer valuesConfigurer) {
        this.valuesConfigurer = valuesConfigurer;
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry
                .addInterceptor(new WebMvcInterceptor(valuesConfigurer))
                .addPathPatterns("/**")
                .excludePathPatterns();
    }

    @Slf4j
    static final class WebMvcInterceptor implements HandlerInterceptor {

        private final ValuesConfigurer valuesConfigurer;

        WebMvcInterceptor(final ValuesConfigurer valuesConfigurer) {
            this.valuesConfigurer = valuesConfigurer;
        }

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
            if (Constants.Default_Spring_Profiles_Active.equals(valuesConfigurer.getProfilesActive())) {
                HttpSession httpSession = request.getSession();
                UserDetails userDetails = new User(Constants.Default_Certifiers_Username, Constants.Default_Certifiers_Password, AuthorityUtils.NO_AUTHORITIES);
                TestingAuthenticationToken authenticationToken = new TestingAuthenticationToken(userDetails, httpSession.getId());
                authenticationToken.setAuthenticated(Boolean.TRUE);
                SecurityContext securityContext = new SecurityContextImpl(authenticationToken);
                httpSession.setAttribute(Constants.Default_Session_Spring_Security_Context, securityContext);
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
        // 设置允许跨域请求的路径
        registry.addMapping("/**")
                // 允许任何域名访问
                // .allowedOrigins("*")
                .allowedOriginPatterns("*")
                // 允许任意方法（GET, POST等）
                .allowedMethods("*")
                // 允许携带认证信息（cookies）
                .allowCredentials(true)
                // 允许所有的请求头
                .allowedHeaders("*")
                // 预检请求的有效期（单位：秒），设置预检请求（OPTIONS）的结果可以被缓存1小时，避免每次请求都发送预检请求
                .maxAge(3600);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

    }
}


