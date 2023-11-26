package com.later.horizon.work.repository;

import com.later.horizon.core.repository.jpa.IJpaRepository;
import com.later.horizon.work.entity.Oauth2ClientTokenEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface IOauth2ClientTokenRepository extends IJpaRepository<Oauth2ClientTokenEntity, Long> {
}
