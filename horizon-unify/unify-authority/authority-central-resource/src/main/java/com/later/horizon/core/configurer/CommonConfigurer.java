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

    @Value("${static-file.ignored-uris}")
    private String[] staticFileIgnoredUris;

    @Value("${request-white.ignored-uris}")
    private String[] requestWhiteIgnoredUris;

    @Value("${authority.client-id}")
    private String authorityClientId;

    @Value("${authority.client-secret}")
    private String authorityClientSecret;

    @Value("${authority.resource-id}")
    private String authorityResourceId;

    @Value("${authority.resource-check-token-endpoint-url}")
    private String authorityResourceCheckTokenEndpointUrl;
}