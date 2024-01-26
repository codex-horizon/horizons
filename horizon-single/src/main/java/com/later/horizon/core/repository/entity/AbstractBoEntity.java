package com.later.horizon.core.repository.entity;

import lombok.Data;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 抽象实体基类
 */
@Data
@MappedSuperclass
public abstract class AbstractBoEntity implements Serializable {

    private static final long serialVersionUUID = 1L;

    // private String id;
    private Long id;

    private String createdBy;

    private Timestamp createdDate;

    private String lastModifiedBy;

    private Timestamp lastModifiedDate;

    private Integer state;

}
