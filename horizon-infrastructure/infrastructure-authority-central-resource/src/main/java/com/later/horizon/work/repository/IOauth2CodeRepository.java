package com.later.horizon.work.repository;

import com.later.horizon.core.repository.jpa.IJpaRepository;
import com.later.horizon.work.entity.Oauth2CodeEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface IOauth2CodeRepository extends IJpaRepository<Oauth2CodeEntity, Long> {
}
