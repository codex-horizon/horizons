package com.later.horizon.core.configurer;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import java.io.Serializable;

@Data
@Configuration
@PropertySources({
        @PropertySource(
                name = "common-${spring.profiles.active}.properties", value = {"classpath:configurer/common-${spring.profiles.active}.properties"},
                encoding = "UTF-8", ignoreResourceNotFound = false
        ),
        @PropertySource(
                name = "common-${spring.profiles.active}.properties", value = {"classpath:configurer/common-${spring.profiles.active}.properties"},
                encoding = "UTF-8", ignoreResourceNotFound = false
        )
})
public class CommonConfigurer implements Serializable {

    private static final long serialVersionUUID = 1L;

    @Value("${application.name}")
    private String applicationName;

    @Value("${request-white.ignored-uris}")
    private String[] requestWhiteIgnoredUris;

    @Value("${authority-central.url}")
    private String authorityCentralUrl;

    @Value("${authority-central.client.authorized-grant-types}")
    private String[] authorityCentralClientAuthorizedGrantTypes;

    @Value("${authority-central.client-id}")
    private String authorityCentralClientId;

    @Value("${authority-central.client-secret}")
    private String authorityCentralClientSecret;

    @Value("${authority-central.client.redirect-url}")
    private String authorityCentralClientRedirectUrl;

    @Value("${authority-central.client.scope}")
    private String authorityCentralClientScope;

    @Value("${authority-central.resource-id}")
    private String authorityCentralResourceId;

    @Value("${authority-central.url}/oauth/check_token")
    private String authorityCentralResourceCheckTokenEndpointUrl;
}
