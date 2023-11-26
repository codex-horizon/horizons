package com.later.horizon.work.entity;

import com.later.horizon.core.repository.entity.AbstractPoEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Table;

import javax.persistence.Entity;
import java.io.Serializable;
import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@javax.persistence.Table(name = "oauth_approvals")
@Table(appliesTo = "oauth_approvals", comment = "OAuth2批准表")
public class Oauth2ApprovalsEntity extends AbstractPoEntity implements Serializable {

    private static final long serialVersionUUID = 1L;

    // @Column(name = "userId", unique = true, nullable = false, columnDefinition = "varchar(256) comment '用户ID(登录用户名)'")
    private String userId;

    // @Column(name = "clientId", unique = true, nullable = false, columnDefinition = "varchar(256) comment '客户端ID'")
    private String clientId;

    // @Column(name = "scope", unique = true, nullable = false, columnDefinition = "varchar(256) comment '客户端申请的权限范围'")
    private String scope;

    // @Column(name = "status", unique = true, nullable = false, columnDefinition = "varchar(10) comment '批准状态'")
    private String status;

    // @Column(name = "expiresAt", nullable = false, columnDefinition = "datetime comment '到期时间'")
    private Timestamp expiresAt;

    // @Column(name = "lastModifiedAt", nullable = false, columnDefinition = "datetime comment '上次修改时间'")
    private Timestamp lastModifiedDate;

}
