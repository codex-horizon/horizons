package com.later.horizon.work.service;

import org.springframework.security.oauth2.provider.ClientDetails;

public interface IClientDetailsService {

    void add(ClientDetails clientDetails);

    void remove(String clientId);

    void update(ClientDetails clientDetails);

    ClientDetails load(String clientId);


}
