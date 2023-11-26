package com.later.horizon.work.service.impl;

import com.later.horizon.work.repository.IOauth2RefreshTokenRepository;
import com.later.horizon.work.service.IOauth2RefreshTokenService;
import org.springframework.stereotype.Service;

@Service
public class Oauth2RefreshTokenService implements IOauth2RefreshTokenService {

    private final IOauth2RefreshTokenRepository iOauth2RefreshTokenRepository;

    Oauth2RefreshTokenService(final IOauth2RefreshTokenRepository iOauth2RefreshTokenRepository){
        this.iOauth2RefreshTokenRepository = iOauth2RefreshTokenRepository;
    }
}
