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
@javax.persistence.Table(name = "oauth_user_details")
@Table(appliesTo = "oauth_user_details", comment = "OAuth2用户表")
public class Oauth2UserDetailsEntity extends AbstractPoEntity implements Serializable {

    private static final long serialVersionUUID = 1L;

    @Column(name = "username", columnDefinition = "varchar(256) comment '用户名'")
    private String username;

    @Column(name = "password", columnDefinition = "varchar(256) comment '密码'")
    private String password;

}
