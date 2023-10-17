package com.later.horizon.core.repository.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 抽象实体基类
 */
@Data
@MappedSuperclass
public abstract class AbstractVoEntity implements Serializable {

    private static final long serialVersionUUID = 1L;

    private String id;

    private String createdBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp createdDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp lastModifiedDate;

    private Integer status;

}
