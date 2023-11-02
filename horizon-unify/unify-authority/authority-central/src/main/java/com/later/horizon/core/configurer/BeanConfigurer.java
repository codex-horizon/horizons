package com.later.horizon.core.configurer;

import com.later.horizon.common.constants.Constants;
import com.later.horizon.common.converter.Converter;
import com.later.horizon.common.converter.IConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.Optional;

@Slf4j
@Configuration
public class BeanConfigurer {

    private final DataSource dataSource;

    private final Environment environment;

    BeanConfigurer(final DataSource dataSource,
                   final Environment environment) {
        this.dataSource = dataSource;
        this.environment = environment;
    }

    @Bean
    public AuditorAware<Object> auditorAware() {
        return () -> Optional.of("currentAuditor");
    }

    @Bean
    public IConverter iConverter() {
        return new Converter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter() {
            @Override
            public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
                // converter.setSigningKey(this.getClass().getName());
                // converter.setVerifierKey(this.getClass().getName());
                return super.enhance(oAuth2AccessToken, oAuth2Authentication);
            }
        };
        String cfgPlaintextDecrypt = environment.getProperty(Constants.Env_Cfg_PlaintextDecrypt);
        if (StringUtils.hasText(cfgPlaintextDecrypt) && Boolean.parseBoolean(cfgPlaintextDecrypt)) {
            String rsaPasswordSeed = environment.getProperty(Constants.Env_RSA_PasswordSeed);
            if (StringUtils.hasText(rsaPasswordSeed)) {
                try {
                    KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
                    SecureRandom secureRandom = new SecureRandom();
                    secureRandom.setSeed(rsaPasswordSeed.getBytes(StandardCharsets.UTF_8));
                    keyPairGen.initialize(1024, secureRandom);
                    converter.setKeyPair(keyPairGen.generateKeyPair());
                } catch (NoSuchAlgorithmException ignored) {
                }
            }
        }
        return converter;
    }

    @Bean
    public JdbcApprovalStore jdbcApprovalStore() {
        return new JdbcApprovalStore(dataSource);
    }

    @Bean
    public JdbcTokenStore jdbcTokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    @Bean
    public JdbcAuthorizationCodeServices jdbcAuthorizationCodeServices() {
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    @Bean
    public JdbcClientDetailsService jdbcClientDetailsService() {
        return new JdbcClientDetailsService(dataSource);
    }

    @Bean
    public DefaultTokenServices defaultTokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();

        defaultTokenServices.setTokenStore(jdbcTokenStore());
        defaultTokenServices.setSupportRefreshToken(Boolean.TRUE);
        defaultTokenServices.setReuseRefreshToken(Boolean.TRUE);
        defaultTokenServices.setClientDetailsService(jdbcClientDetailsService());

        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Collections.singletonList(jwtAccessTokenConverter()));
        defaultTokenServices.setTokenEnhancer(tokenEnhancerChain);

        return defaultTokenServices;
    }

}
