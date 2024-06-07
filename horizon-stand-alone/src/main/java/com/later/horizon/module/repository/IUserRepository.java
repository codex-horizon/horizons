package com.later.horizon.module.repository;

import com.later.horizon.core.repository.jpa.IJpaRepository;
import com.later.horizon.module.entity.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends IJpaRepository<UserEntity, Long> {
}
