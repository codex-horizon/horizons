package com.later.horizon.core.repository.entity;

import com.later.horizon.common.constants.Constants;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 抽象实体基类
 */
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractPoEntity implements Serializable {

    private static final long serialVersionUUID = 1L;

    // @Id
    // JPA标准-主键标识
    // @GeneratedValue(generator = "uuid2")
    // JPA标准-通用策略生成器
    // @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    // 自定义主键生成策略
    // @Column(name = "id", unique = true, nullable = false, length = 36, columnDefinition = "varchar(36) comment '主键|无业务属性'")
    // private String id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, columnDefinition = "bigint(20) comment '标识'")
    private Long id;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tenant_id", unique = true, nullable = false, columnDefinition = "bigint(20) comment '租户标识'")
    private Long tenantId;

    @CreatedBy
    @Column(name = "created_by", nullable = false, columnDefinition = "varchar(20) comment '创建人（标识）'")
    private String createdBy;

    @CreatedDate
    @Column(name = "created_date", nullable = false, columnDefinition = "timestamp default current_timestamp comment '创建时间'")
    private Timestamp createdDate;

    @LastModifiedBy
    @Column(name = "last_modified_by", nullable = false, columnDefinition = "varchar(20) comment '最后修改人（标识）'")
    private String lastModifiedBy;

    @LastModifiedDate
    @Column(name = "last_modified_date", nullable = false, columnDefinition = "timestamp default current_timestamp comment '最后修改时间'")
    private Timestamp lastModifiedDate;

    @Column(name = "status", nullable = false, columnDefinition = "tinyint(1) default 1 comment '数据状态'")
    private Long status = Constants.ProveStatus.Data_Status_NORMAL.getStatus();

}
