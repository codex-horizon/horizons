package com.later.horizon.core.security;

import com.later.horizon.core.configurer.CommonConfigurer;
import com.later.horizon.core.security.filters.UsernamePasswordAuthenticationFilter;
import com.later.horizon.core.security.handler.AuthenticationFailureHandler;
import com.later.horizon.core.security.handler.AuthenticationSuccessHandler;
import com.later.horizon.core.security.handler.LogoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
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

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(commonConfigurer.getStaticFilesIgnoredUris());
    }

    @Override
    protected void configure(final HttpSecurity httpSecurity) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("in WebSecurityConfigurer configure.");
        }
        httpSecurity
                .addFilterBefore(new UsernamePasswordAuthenticationFilter(), org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .anyRequest().fullyAuthenticated()
                .and()
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                .formLogin().loginPage("/login_view").loginProcessingUrl("/login").permitAll()
                .successForwardUrl("/login_succeed_view").failureForwardUrl("/login_failed_view")
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout", HttpMethod.GET.name())).logoutSuccessUrl("/logout_view").permitAll();
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
