package com.later.horizon.core.security;

import com.later.horizon.core.configurer.CommonConfigurer;
import com.later.horizon.core.security.filters.UsernamePasswordAuthenticationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Slf4j
@EnableWebSecurity
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    private final CommonConfigurer commonConfigurer;

    WebSecurityConfigurer(final CommonConfigurer commonConfigurer) {
        this.commonConfigurer = commonConfigurer;
    }

    /**
     * 注意：【defaultSuccessUrl】与【successForwardUrl】二选其一，两者都用仅生效【defaultSuccessUrl】，且不论True还是False一律强制默认成功页；
     * 当使用 Spring Security 使用【defaultSuccessUrl,Boolean】时，当True时，强制默认成功页；当False时，访问指定页面，用户未登入，跳转至登入页面，如果登入成功，（GET形式）跳转至用户访问指定页面，用户访问登入页面，默认的跳转页面；
     * 当使用 Spring Security 使用【successForwardUrl】时，会以及POST方式进入该函数，必须重定向到主页或其他页；
     * 当使用 Spring Security 使用【failureForwardUrl】时，会以及POST方式进入该函数，进入到失败页；
     *
     * @param httpSecurity 安全配置
     * @throws Exception 安全配置异常
     */
    @Override
    protected void configure(final HttpSecurity httpSecurity) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("in WebSecurityConfigurer configure.");
        }
        httpSecurity
                .addFilterBefore(new UsernamePasswordAuthenticationFilter(), org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class)
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                .authorizeRequests().antMatchers(HttpMethod.GET, commonConfigurer.getStaticFilesIgnoredUris()).permitAll().anyRequest().fullyAuthenticated()
                .and()
                .formLogin().loginPage("/login_view").permitAll().loginProcessingUrl("/login").defaultSuccessUrl("/index_view", Boolean.FALSE)/*.successForwardUrl("/login_succeed_view")*/.failureForwardUrl("/login_failed_view")
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout", HttpMethod.POST.name())).logoutSuccessUrl("/login_view");
    }

    /**
     * 同时支持密码模式
     */
    @Bean
    @Primary
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
