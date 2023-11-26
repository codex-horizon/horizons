package com.later.horizon.work.service.impl;

import com.later.horizon.common.constants.Constants;
import com.later.horizon.common.converter.IConverter;
import com.later.horizon.common.exception.BizException;
import com.later.horizon.common.helper.RequestHelper;
import com.later.horizon.work.bo.UserDetailsBo;
import com.later.horizon.work.entity.UserDetailsEntity;
import com.later.horizon.work.repository.IUserDetailsRepository;
import com.later.horizon.work.service.IUserDetailsService;
import org.springframework.data.domain.Example;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserDetailsService implements IUserDetailsService {

    private final IConverter iConverter;

    private final IUserDetailsRepository iUserDetailsRepository;

    private final PasswordEncoder passwordEncoder;

    UserDetailsService(final IConverter iConverter,
                       final IUserDetailsRepository iUserDetailsRepository,
                       final PasswordEncoder passwordEncoder) {
        this.iConverter = iConverter;
        this.iUserDetailsRepository = iUserDetailsRepository;
        this.passwordEncoder = passwordEncoder;
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
        // 预判用户是否存在
        UserDetailsEntity userDetailsEntity = new UserDetailsEntity();
        userDetailsEntity.setUsername(username);
        userDetailsEntity = iUserDetailsRepository.findOne(Example.of(userDetailsEntity)).orElseThrow(() -> new UsernameNotFoundException(Constants.BizStatus.Sso_User_Not_Exists.getMessage()));
        UserDetailsBo userDetailsBo = iConverter.convert(userDetailsEntity, UserDetailsBo.class);
        HttpServletRequest request = RequestHelper.getHttpServletRequest();
        // 预判系统是否授权

        // 预判用户是否授权

        // 预判验码是否命中
        String code = request.getParameter(Constants.Form_Parameter_Code_Lowercase);
        if(!StringUtils.hasText(code)) {
            throw new BizException(Constants.BizStatus.Sso_User_Password_Incorrect);
        }

        // 预判密码是否正确
        String encryptPassword = userDetailsBo.getPassword();
        String password = request.getParameter(Constants.Form_Parameter_Password_Lowercase);
        if (!passwordEncoder.matches(password, encryptPassword)) {
            throw new BizException(Constants.BizStatus.Sso_User_Password_Incorrect);
        }

        // 预判完全许可放行
        return new org.springframework.security.core.userdetails.User(
                username,
                userDetailsBo.getPassword(),
                AuthorityUtils.NO_AUTHORITIES
        );
    }

}
