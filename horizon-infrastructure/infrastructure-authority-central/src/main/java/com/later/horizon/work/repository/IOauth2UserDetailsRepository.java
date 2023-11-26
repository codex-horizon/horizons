package com.later.horizon.work.repository;

import com.later.horizon.core.repository.jpa.IJpaRepository;
import com.later.horizon.work.entity.Oauth2UserDetailsEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface IOauth2UserDetailsRepository extends IJpaRepository<Oauth2UserDetailsEntity, Long> {
}
