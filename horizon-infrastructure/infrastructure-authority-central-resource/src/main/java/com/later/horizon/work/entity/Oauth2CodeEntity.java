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
@javax.persistence.Table(name = "oauth_code")
@Table(appliesTo = "oauth_code", comment = "OAuth2授权码表")
public class Oauth2CodeEntity extends AbstractPoEntity implements Serializable {

    private static final long serialVersionUUID = 1L;

    @Column(name = "code", columnDefinition = "varchar(256) comment '授权码(未加密)'")
    private String code;

    @Column(name = "authentication", columnDefinition = "blob comment 'AuthorizationRequestHolder.java对象序列化后的二进制数据'")
    private byte[] authentication;

}
