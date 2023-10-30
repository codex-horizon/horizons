package com.later.horizon.core.configurer;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import java.io.Serializable;
import java.util.List;

@Data
@Configuration
@PropertySources({
        @PropertySource(
                name = "common-${spring.profiles.active}.properties", value = {"classpath:configurer/common-${spring.profiles.active}.properties"},
                encoding = "UTF-8", ignoreResourceNotFound = true
        ),
        @PropertySource(
                name = "common-${spring.profiles.active}.properties", value = {"classpath:configurer/common-${spring.profiles.active}.properties"},
                encoding = "UTF-8", ignoreResourceNotFound = true
        )
})
public class CommonConfigurer implements Serializable {

    private static final long serialVersionUUID = 1L;

    @Value("${Authority.ApplicationName}")
    private String authorityApplicationName;

    @Value("${Authority.ignoredUris}")
    private List<String> authorityIgnoredUris;

    @Value("${Authority.Origin}")
    private String authorityOrigin;

    @Value("${Authority.ClientId}")
    private String authorityClientId;

    @Value("${Authority.ClientSecret}")
    private String authorityClientSecret;

    @Value("${Authority.ClientRedirectUrl}")
    private String authorityClientRedirectUrl;

    @Value("${Authority.AuthorizationTypes}")
    private List<String> authorityAuthorizationTypes;

    @Value("${Authority.OpenCsrf}")
    private boolean authorityOpenCsrf;

    @Value("${StaticFiles.ignoredUris}")
    private String[] staticFilesIgnoredUris;

}
