package com.later.horizon.core.configurer.authorization;

import com.later.horizon.common.constants.Constants;
import com.later.horizon.work.service.IOauth2UserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

@SuppressWarnings(Constants.Default_Suppress_Warnings_Deprecation)
@Slf4j
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfigurer extends AuthorizationServerConfigurerAdapter {

    private final DataSource dataSource;

    private final JdbcApprovalStore jdbcApprovalStore;

    private final JdbcTokenStore jdbcTokenStore;

    private final JdbcAuthorizationCodeServices jdbcAuthorizationCodeServices;

    private final JdbcClientDetailsService jdbcClientDetailsService;

    private final AuthenticationManager authenticationManager;

    private final DefaultTokenServices defaultTokenServices;

    private final IOauth2UserDetailsService iOauth2UserDetailsService;

    AuthorizationServerConfigurer(final DataSource dataSource,
                                  final JdbcApprovalStore jdbcApprovalStore,
                                  final JdbcTokenStore jdbcTokenStore,
                                  final JdbcAuthorizationCodeServices jdbcAuthorizationCodeServices,
                                  final JdbcClientDetailsService jdbcClientDetailsService,
                                  final AuthenticationManager authenticationManager,
                                  final DefaultTokenServices defaultTokenServices,
                                  final IOauth2UserDetailsService iOauth2UserDetailsService) {
        this.dataSource = dataSource;
        this.jdbcApprovalStore = jdbcApprovalStore;
        this.jdbcTokenStore = jdbcTokenStore;
        this.jdbcAuthorizationCodeServices = jdbcAuthorizationCodeServices;
        this.jdbcClientDetailsService = jdbcClientDetailsService;
        this.authenticationManager = authenticationManager;
        this.defaultTokenServices = defaultTokenServices;
        this.iOauth2UserDetailsService = iOauth2UserDetailsService;
    }

    @Override
    public void configure(final AuthorizationServerSecurityConfigurer security) {
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("permitAll()")
                .allowFormAuthenticationForClients();
    }

    @Override
    public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource);
    }

    /**
     * tokenServices：org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer#createDefaultTokenServices
     *
     * @param endpoints the endpoints configurer
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.userDetailsService(iOauth2UserDetailsService) // oauth_user_details
                .reuseRefreshTokens(Boolean.FALSE);

        // 支持密码模式；
        endpoints.authenticationManager(authenticationManager);

        // 支持增强型JWT；
        endpoints.tokenServices(defaultTokenServices)
                // oauth_approvals
                .approvalStore(jdbcApprovalStore)
                // oauth_access_token、oauth_refresh_token
                .tokenStore(jdbcTokenStore)
                // oauth_code
                .authorizationCodeServices(jdbcAuthorizationCodeServices)
                // oauth_client_details
                .setClientDetailsService(jdbcClientDetailsService);

    }


}
