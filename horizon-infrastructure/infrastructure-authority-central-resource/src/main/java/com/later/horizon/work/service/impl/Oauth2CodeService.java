package com.later.horizon.work.service.impl;

import com.later.horizon.work.repository.IOauth2CodeRepository;
import com.later.horizon.work.service.IOauth2CodeService;
import org.springframework.stereotype.Service;

@Service
public class Oauth2CodeService implements IOauth2CodeService {

    private final IOauth2CodeRepository iOauth2CodeRepository;

    Oauth2CodeService(final IOauth2CodeRepository iOauth2CodeRepository){
        this.iOauth2CodeRepository = iOauth2CodeRepository;
    }
}
