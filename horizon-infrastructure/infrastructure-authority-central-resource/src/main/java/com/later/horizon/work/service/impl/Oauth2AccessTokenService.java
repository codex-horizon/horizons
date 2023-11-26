package com.later.horizon.work.service.impl;

import com.later.horizon.work.repository.IOauth2AccessTokenRepository;
import com.later.horizon.work.service.IOauth2AccessTokenService;
import org.springframework.stereotype.Service;

@Service
public class Oauth2AccessTokenService implements IOauth2AccessTokenService {

    private final IOauth2AccessTokenRepository iOauth2AccessTokenRepository;

    Oauth2AccessTokenService(final IOauth2AccessTokenRepository iOauth2AccessTokenRepository) {
        this.iOauth2AccessTokenRepository = iOauth2AccessTokenRepository;
    }


}
