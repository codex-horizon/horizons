package com.later.horizon.core.configurer.security;

import com.later.horizon.core.configurer.ValuesConfigurer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Slf4j
@EnableWebSecurity
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    private final ValuesConfigurer valuesConfigurer;

    WebSecurityConfigurer(final ValuesConfigurer valuesConfigurer) {
        this.valuesConfigurer = valuesConfigurer;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(valuesConfigurer.getSecurityIgnoredUris()).permitAll()
                // authenticated: 这个状态表示用户已经通过了认证。这通常意味着用户提供了有效的用户名和密码，并且这些凭证与数据库中的记录相匹配。
                // 但是，这并不意味着用户已经完全完成了认证流程。例如，用户可能已经通过了基本认证，但还需要完成两步验证或其他额外的安全步骤。
                // .anyRequest().authenticated();

                // fullyAuthenticated: 这个状态表示用户不仅通过了认证，而且已经完成了所有的认证步骤。
                // 这意味着用户不仅提供了有效的用户名和密码，而且已经完成了所有其他的认证流程，如两步验证或额外的安全验证。
                .anyRequest().fullyAuthenticated();
    }
}
