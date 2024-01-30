package com.later.horizon.core.configurer.resource;

import com.later.horizon.core.configurer.ValuesConfigurer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

@Slf4j
@Configuration
@EnableResourceServer
public class ResourceServerConfigurer extends ResourceServerConfigurerAdapter {

    private final ValuesConfigurer valuesConfigurer;

    ResourceServerConfigurer(final ValuesConfigurer valuesConfigurer) {
        this.valuesConfigurer = valuesConfigurer;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources
                .resourceId(valuesConfigurer.getAuthorityCentralResourceId())
                .tokenServices(new RemoteTokenServices() {{
                    setClientId(valuesConfigurer.getAuthorityCentralClientId());
                    setClientSecret(valuesConfigurer.getAuthorityCentralClientSecret());
                    setCheckTokenEndpointUrl(valuesConfigurer.getAuthorityCentralResourceCheckTokenEndpointUrl());
                }});
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(valuesConfigurer.getRequestWhiteIgnoredUris()).permitAll()
                .anyRequest().fullyAuthenticated();
    }

}