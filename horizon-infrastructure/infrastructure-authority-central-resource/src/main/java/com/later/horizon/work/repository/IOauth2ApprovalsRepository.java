package com.later.horizon.work.repository;

import com.later.horizon.core.repository.jpa.IJpaRepository;
import com.later.horizon.work.entity.Oauth2ApprovalsEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface IOauth2ApprovalsRepository extends IJpaRepository<Oauth2ApprovalsEntity, Long> {
}
