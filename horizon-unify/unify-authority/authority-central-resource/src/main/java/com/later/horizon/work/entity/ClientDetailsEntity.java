package com.later.horizon.work.entity;

import com.later.horizon.core.repository.entity.AbstractPoEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Table;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@javax.persistence.Table(name = "oauth_client_details")
@Table(appliesTo = "oauth_client_details", comment = "客户端表")
public class ClientDetailsEntity extends AbstractPoEntity implements Serializable {

    @Column(name = "client_id", unique = true, nullable = false, columnDefinition = "varchar(256) comment '客户端ID'")
    private String clientId;

    @Column(name = "resource_ids", unique = true, nullable = false, columnDefinition = "varchar(256) comment '资源ID集合，多个资源时用英文逗号分隔'")
    private String resourceIds;

    @Column(name = "client_secret", unique = true, nullable = false, columnDefinition = "varchar(256) comment '客户端密匙'")
    private String clientSecret;

    @Column(name = "scope", unique = true, nullable = false, columnDefinition = "varchar(256) comment '客户端申请的权限范围'")
    private String scope;

    @Column(name = "authorized_grant_types", unique = true, nullable = false, columnDefinition = "varchar(256) comment '客户端支持的Grant_Type'")
    private String authorizedGrantTypes;

    @Column(name = "web_server_redirect_uri", unique = true, nullable = false, columnDefinition = "varchar(256) comment '重定向Url'")
    private String webServerRedirectUri;

    @Column(name = "authorities", unique = true, nullable = false, columnDefinition = "varchar(256) comment '客户端所拥有的SpringSecurity的权限值，多个用英文逗号分隔'")
    private String authorities;

    @Column(name = "access_token_validity", unique = true, nullable = false, columnDefinition = "int(11) comment '访问令牌有效时间值(单位秒)'")
    private Integer accessTokenValidity;

    @Column(name = "refresh_token_validity", unique = true, nullable = false, columnDefinition = "int(11) comment '更新令牌有效时间值(单位秒)'")
    private Integer refreshTokenValidity;

    @Column(name = "additional_information", unique = true, nullable = false, columnDefinition = "varchar(256) comment '预留字段'")
    private String additionalInformation;

    @Column(name = "autoapprove", unique = true, nullable = false, columnDefinition = "varchar(256) comment '用户是否自动Approval操作'")
    private String autoApprove;

}
