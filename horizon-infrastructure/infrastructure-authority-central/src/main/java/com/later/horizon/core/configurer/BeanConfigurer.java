package com.later.horizon.core.configurer;

import com.later.horizon.common.constants.Constants;
import com.later.horizon.common.converter.Converter;
import com.later.horizon.common.converter.IConverter;
import com.later.horizon.common.exception.BusinessException;
import com.later.horizon.common.helper.RSAHelper;
import com.later.horizon.core.filters.TraceIdCorsFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
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
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import java.util.Collections;
import java.util.Optional;

@SuppressWarnings(Constants.Default_Suppress_Warnings_Deprecation)
@Slf4j
@Configuration
public class BeanConfigurer {

    private final DataSource dataSource;

    private final Environment environment;

    private final ValuesConfigurer valuesConfigurer;

    BeanConfigurer(final DataSource dataSource,
                   final Environment environment,
                   final ValuesConfigurer valuesConfigurer) {
        this.dataSource = dataSource;
        this.environment = environment;
        this.valuesConfigurer = valuesConfigurer;
    }

    @Bean
    public AuditorAware<UserDetails> auditorAware() {
        return () -> Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .map(User.class::cast)
                /*.map(User::getUsername)*/;
    }

    @Bean
    public IConverter iConverter() {
        return new Converter();
    }

    @Bean
    public FilterRegistrationBean<TraceIdCorsFilter> traceIdCorsFilter() {
        return new FilterRegistrationBean<TraceIdCorsFilter>() {{
            setFilter(new TraceIdCorsFilter(valuesConfigurer));
            setOrder(Ordered.HIGHEST_PRECEDENCE);
            setUrlPatterns(Collections.singletonList("/*"));
        }};
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // return NoOpPasswordEncoder.getInstance();
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter() {
            @Override
            public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
                OAuth2AccessToken enhance = super.enhance(oAuth2AccessToken, oAuth2Authentication);
                // JwtHelper.decode("");
                return enhance;
            }
        };
        String plaintextDecrypt = environment.getProperty(Constants.Env_Run_PlaintextDecrypt);
        boolean hasPlaintextDecrypt = false;
        try {
            hasPlaintextDecrypt = Boolean.parseBoolean(plaintextDecrypt);
        } catch (Exception ignored) {
        }
        if (hasPlaintextDecrypt) {
            String passwordSeed = environment.getProperty(Constants.Env_Run_PasswordSeed);
            if (StringUtils.hasText(passwordSeed)) {
                try {
                    // 密钥库获取的RSA密钥对
                    KeyPair keyPair = RSAHelper.generatorKeyPair(passwordSeed);

                    // 密钥库获取并设置公钥用于验证JWT令牌
                    RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
                    String publicKeyStr = new String(Base64.getEncoder().encode(publicKey.getEncoded()), StandardCharsets.UTF_8);
                    jwtAccessTokenConverter.setVerifierKey(publicKeyStr);

                    // 设置密钥对用于生成JWT令牌
                    jwtAccessTokenConverter.setKeyPair(keyPair);
                } catch (NoSuchAlgorithmException ignored) {
                }
            } else {
                throw new BusinessException(Constants.ProveProveState.Env_Run_RSA_PasswordSeed_Empty);
            }
        }
        return jwtAccessTokenConverter;
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
        defaultTokenServices.setReuseRefreshToken(Boolean.FALSE);

        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Collections.singletonList(jwtAccessTokenConverter()));
        defaultTokenServices.setTokenEnhancer(tokenEnhancerChain);

        return defaultTokenServices;
    }

}
