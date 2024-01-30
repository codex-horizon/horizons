package com.later.horizon.core.configurer;

import com.later.horizon.common.constants.Constants;
import com.later.horizon.common.converter.Converter;
import com.later.horizon.common.converter.IConverter;
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
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Optional;

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
                return super.enhance(oAuth2AccessToken, oAuth2Authentication);
            }
        };
        String cfgPlaintextDecrypt = environment.getProperty(Constants.Env_Run_PlaintextDecrypt);
        if (StringUtils.hasText(cfgPlaintextDecrypt) && Boolean.parseBoolean(cfgPlaintextDecrypt)) {
            String passwordSeed = environment.getProperty(Constants.Env_Run_PasswordSeed);
            if (StringUtils.hasText(passwordSeed)) {
                try {
                    jwtAccessTokenConverter.setKeyPair(RSAHelper.generatorKeyPair(passwordSeed));
                } catch (NoSuchAlgorithmException ignored) {
                }
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
