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
public class ValuesConfigurer implements Serializable {

    private static final long serialVersionUUID = 1L;

    @Value("${spring.profiles.active}")
    private String profilesActive;

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${request-white.ignored-uris}")
    private String[] requestWhiteIgnoredUris;

    @Value("${OpenEnvironmentVariableEncipher}")
    private Boolean openEnvironmentVariableEncipher;

    @Value("${EnvironmentVariablePasswordSeed}")
    private String environmentVariablePasswordSeed;

    @Value("${OpenRequestUrlEncipher}")
    private Boolean openRequestUrlEncipher;

    @Value("${RequestUrlEncipherPasswordSeed}")
    private String requestUrlEncipherPasswordSeed;


}
