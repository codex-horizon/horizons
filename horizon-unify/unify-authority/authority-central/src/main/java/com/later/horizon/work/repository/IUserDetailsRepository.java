package com.later.horizon.work.repository;

import com.later.horizon.core.repository.jpa.IJpaRepository;
import com.later.horizon.work.entity.UserDetailsEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserDetailsRepository extends IJpaRepository<UserDetailsEntity, String> {
}
