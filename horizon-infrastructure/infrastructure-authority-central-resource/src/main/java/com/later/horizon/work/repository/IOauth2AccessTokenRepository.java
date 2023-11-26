package com.later.horizon.work.repository;

import com.later.horizon.core.repository.jpa.IJpaRepository;
import com.later.horizon.work.entity.Oauth2AccessTokenEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface IOauth2AccessTokenRepository extends IJpaRepository<Oauth2AccessTokenEntity, Long> {
}
