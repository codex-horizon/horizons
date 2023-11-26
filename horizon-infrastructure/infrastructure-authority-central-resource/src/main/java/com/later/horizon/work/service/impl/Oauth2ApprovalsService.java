package com.later.horizon.work.service.impl;

import com.later.horizon.work.repository.IOauth2ApprovalsRepository;
import com.later.horizon.work.service.IOauth2ApprovalsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class Oauth2ApprovalsService implements IOauth2ApprovalsService {

    private final IOauth2ApprovalsRepository iOauth2ApprovalsRepository;

    Oauth2ApprovalsService(final IOauth2ApprovalsRepository iOauth2ApprovalsRepository) {
        this.iOauth2ApprovalsRepository = iOauth2ApprovalsRepository;
    }


}
