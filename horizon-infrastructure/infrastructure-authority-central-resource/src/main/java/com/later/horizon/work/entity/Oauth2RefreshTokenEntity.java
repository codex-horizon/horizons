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
@javax.persistence.Table(name = "oauth_refresh_token")
@Table(appliesTo = "oauth_refresh_token", comment = "OAuth2刷新令牌表")
public class Oauth2RefreshTokenEntity extends AbstractPoEntity implements Serializable {

    private static final long serialVersionUUID = 1L;

    @Column(name = "token_id", columnDefinition = "varchar(256) comment 'MD5加密过的refresh_token的值'")
    private String tokenId;

    @Column(name = "token", columnDefinition = "blob comment 'OAuth2RefreshToken.java对象序列化后的二进制数据'")
    private byte[] token;

    @Column(name = "authentication", columnDefinition = "blob comment 'OAuth2Authentication.java对象序列化后的二进制数据'")
    private byte[] authentication;

}
