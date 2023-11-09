package com.later.horizon.core.configurer.resource;

import com.later.horizon.core.configurer.CommonConfigurer;
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

    private final CommonConfigurer commonConfigurer;

    ResourceServerConfigurer(final CommonConfigurer commonConfigurer) {
        this.commonConfigurer = commonConfigurer;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(commonConfigurer.getAuthorityResourceId()).tokenServices(new RemoteTokenServices() {{
            setClientId(commonConfigurer.getAuthorityClientId());
            setClientSecret(commonConfigurer.getAuthorityClientSecret());
            setCheckTokenEndpointUrl(commonConfigurer.getAuthorityResourceCheckTokenEndpointUrl());
        }});
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(commonConfigurer.getRequestWhiteIgnoredUris()).permitAll()
                .anyRequest().fullyAuthenticated();
    }

}