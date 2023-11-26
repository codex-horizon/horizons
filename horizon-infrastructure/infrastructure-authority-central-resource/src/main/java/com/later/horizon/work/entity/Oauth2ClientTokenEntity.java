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
@javax.persistence.Table(name = "oauth_client_token")
@Table(appliesTo = "oauth_client_token", comment = "OAuth2客户端令牌表")
public class Oauth2ClientTokenEntity extends AbstractPoEntity implements Serializable {

    private static final long serialVersionUUID = 1L;

    @Column(name = "token_id", unique = true, nullable = false, columnDefinition = "varchar(256) comment 'MD5加密的access_token的值'")
    private String tokenId;

    @Column(name = "token", unique = true, nullable = false, columnDefinition = "blob comment 'OAuth2AccessToken.java对象序列化后的二进制数据'")
    private byte[] token;

    @Column(name = "authentication_id", unique = true, nullable = false, columnDefinition = "varchar(256) comment 'MD5加密过的username,client_id,scope'")
    private String authenticationId;

    @Column(name = "user_name", unique = true, nullable = false, columnDefinition = "varchar(256) comment '登录的用户名'")
    private String username;

    @Column(name = "client_id", unique = true, nullable = false, columnDefinition = "varchar(256) comment '客户端ID'")
    private String clientId;

}
