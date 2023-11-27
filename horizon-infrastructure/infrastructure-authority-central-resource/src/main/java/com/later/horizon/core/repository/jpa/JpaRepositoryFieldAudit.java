package com.later.horizon.core.repository.jpa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Slf4j
@Component
public class JpaRepositoryFieldAudit {

    /**
     * 新增数据时，
     * 填充创建人和创建时间
     */
    @PrePersist
    public void prePersist(Object object) {
        log.info("in JpaRepositoryFieldAudit prePersist.");
    }

    /**
     * 更新数据时，
     * 填充更新人和更新时间
     */
    @PreUpdate
    public void preUpdate(Object object) {
        log.info("in JpaRepositoryFieldAudit preUpdate.");
    }


    /**
     * 新增数据之后的操作
     */
    @PostPersist
    public void postPersist(Object object) {
        log.info("in JpaRepositoryFieldAudit postPersist.");
    }

    /**
     * 更新数据之后的操作
     */
    @PostUpdate
    public void postUpdate(Object object) {
        log.info("in JpaRepositoryFieldAudit postUpdate.");
    }
}
