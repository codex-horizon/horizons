package com.later.horizon.module.entity;

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
@javax.persistence.Table(name = "user")
@Table(appliesTo = "user", comment = "用户表")
public class UserEntity extends AbstractPoEntity implements Serializable {

    private static final long serialVersionUUID = 1L;

    @Column(name = "username", nullable = false, columnDefinition = "varchar(256) comment '账户'")
    private String username;

    @Column(name = "password", nullable = false, columnDefinition = "varchar(256) comment '密码'")
    private String password;

}
