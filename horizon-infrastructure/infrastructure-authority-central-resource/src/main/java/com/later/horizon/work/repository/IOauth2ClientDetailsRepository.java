package com.later.horizon.work.repository;

import com.later.horizon.core.repository.jpa.IJpaRepository;
import com.later.horizon.work.entity.Oauth2ClientDetailsEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface IOauth2ClientDetailsRepository extends IJpaRepository<Oauth2ClientDetailsEntity, Long> {
}
