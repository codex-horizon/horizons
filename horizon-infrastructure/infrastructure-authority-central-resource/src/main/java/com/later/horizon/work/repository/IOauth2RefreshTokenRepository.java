package com.later.horizon.work.repository;

import com.later.horizon.work.entity.Oauth2RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOauth2RefreshTokenRepository extends JpaRepository<Oauth2RefreshTokenEntity, Long> {
}
