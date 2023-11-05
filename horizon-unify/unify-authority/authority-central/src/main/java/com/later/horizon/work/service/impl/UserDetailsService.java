package com.later.horizon.work.service.impl;

import com.later.horizon.common.constants.Constants;
import com.later.horizon.work.entity.UserDetailsEntity;
import com.later.horizon.work.repository.IUserDetailsRepository;
import com.later.horizon.work.service.IUserDetailsService;
import org.springframework.data.domain.Example;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsService implements IUserDetailsService {

    private final IUserDetailsRepository iUserDetailsRepository;

    UserDetailsService(final IUserDetailsRepository iUserDetailsRepository) {
        this.iUserDetailsRepository = iUserDetailsRepository;
    }

    /**
     * org.springframework.security.authentication.dao.DaoAuthenticationProvider#additionalAuthenticationChecks
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetailsEntity entity = new UserDetailsEntity();
        entity.setUsername(username);
        Optional<UserDetailsEntity> optionalUserDetailsEntity = iUserDetailsRepository.findOne(Example.of(entity));
        if (optionalUserDetailsEntity.isPresent()) {
            UserDetailsEntity finalEntity = optionalUserDetailsEntity.get();
            return new org.springframework.security.core.userdetails.User(
                    username,
                    finalEntity.getPassword(),
                    AuthorityUtils.NO_AUTHORITIES
            );
        }
        throw new UsernameNotFoundException(Constants.BizStatus.Sso_User_Not_Found.getMessage());
    }

}
