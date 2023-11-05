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

    @Value("${ApplicationName}")
    private String applicationName;

    @Value("${StaticFile.ignoredUris}")
    private String[] staticFileIgnoredUris;

    @Value("${RequestWhite.ignoredUris}")
    private String[] requestWhiteIgnoredUris;

    @Value("${Authority.ClientId}")
    private String authorityClientId;

    @Value("${RequestWhite.ClientSecret}")
    private String authorityClientSecret;

    @Value("${Authority.ResourceId}")
    private String authorityResourceId;

    @Value("${RequestWhite.ResourceCheckTokenEndpointUrl}")
    private String authorityResourceCheckTokenEndpointUrl;
}
