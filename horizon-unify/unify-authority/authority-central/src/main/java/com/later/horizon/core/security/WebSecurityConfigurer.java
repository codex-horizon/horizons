package com.later.horizon.core.security;

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

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/favicon.ico","/static/**");
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("in WebSecurityConfigurer configure.");
        }
        http.addFilterBefore(new UsernamePasswordAuthenticationFilter(), org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);

        http.formLogin().loginPage("/loginView").loginProcessingUrl("/login").successHandler(new AuthenticationSuccessHandler()).failureHandler(new AuthenticationFailureHandler()).permitAll();
        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());

        http.authorizeRequests().antMatchers("/authority").permitAll().anyRequest().authenticated();

        http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout", HttpMethod.GET.name())).permitAll().addLogoutHandler(new LogoutHandler()).logoutSuccessUrl("/logoutView").permitAll();

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
