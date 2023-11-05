package com.later.horizon.work.service.impl;

import com.later.horizon.work.service.IClientDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Slf4j
@Service
public class ClientDetailsService extends JdbcClientDetailsService implements IClientDetailsService {

    public ClientDetailsService(final DataSource dataSource) {
        super(dataSource);
    }


    @Override
    public void add(ClientDetails clientDetails) {
        super.addClientDetails(clientDetails);
    }

    @Override
    public void remove(String clientId) {
        super.removeClientDetails(clientId);
    }

    @Override
    public void update(ClientDetails clientDetails) {
        super.updateClientDetails(clientDetails);
    }

    @Override
    public ClientDetails load(String clientId) {
        return super.loadClientByClientId(clientId);
    }
}
