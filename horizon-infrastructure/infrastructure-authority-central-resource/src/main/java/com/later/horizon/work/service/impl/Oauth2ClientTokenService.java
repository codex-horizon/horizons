package com.later.horizon.work.service.impl;

import com.later.horizon.work.repository.IOauth2ClientTokenRepository;
import com.later.horizon.work.service.IOauth2ClientTokenService;
import org.springframework.stereotype.Service;

@Service
public class Oauth2ClientTokenService implements IOauth2ClientTokenService {

    private final IOauth2ClientTokenRepository iOauth2ClientTokenRepository;

    Oauth2ClientTokenService(final IOauth2ClientTokenRepository iOauth2ClientTokenRepository) {
        this.iOauth2ClientTokenRepository = iOauth2ClientTokenRepository;
    }

}
