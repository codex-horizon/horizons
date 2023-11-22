package com.later.horizon.work.bo;

import com.later.horizon.core.repository.entity.AbstractPoEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Collection;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@javax.persistence.Table(name = "oauth_user_details")
@Table(appliesTo = "oauth_user_details", comment = "认证授权用户表")
public class UserDetailsBo extends AbstractPoEntity implements UserDetails, Serializable {

    private static final long serialVersionUUID = 1L;

    @Column(name = "username", nullable = false, columnDefinition = "varchar(255) comment '用户名'")
    private String username;

    @Column(name = "password", nullable = false, columnDefinition = "varchar(255) comment '密码'")
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.NO_AUTHORITIES;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
