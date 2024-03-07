package com.later.horizon.work.service.impl;

import com.later.horizon.common.constants.Constants;
import com.later.horizon.common.converter.IConverter;
import com.later.horizon.common.exception.BusinessException;
import com.later.horizon.common.helper.CommonHelper;
import com.later.horizon.common.restful.IPageableResponse;
import com.later.horizon.common.restful.PageableQo;
import com.later.horizon.work.bo.Oauth2UserDetailsBo;
import com.later.horizon.work.entity.Oauth2UserDetailsEntity;
import com.later.horizon.work.qry.Oauth2UserDetailsQo;
import com.later.horizon.work.repository.IOauth2UserDetailsRepository;
import com.later.horizon.work.service.IOauth2UserDetailsService;
import com.later.horizon.work.vo.Oauth2UserDetailsVo;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class Oauth2UserDetailsService implements IOauth2UserDetailsService {

    private final IConverter iConverter;

    private final IOauth2UserDetailsRepository iOauth2UserDetailsRepository;

    private final PasswordEncoder passwordEncoder;

    Oauth2UserDetailsService(final IConverter iConverter,
                             final IOauth2UserDetailsRepository iOauth2UserDetailsRepository,
                             final PasswordEncoder passwordEncoder) {
        this.iConverter = iConverter;
        this.iOauth2UserDetailsRepository = iOauth2UserDetailsRepository;
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
        Oauth2UserDetailsEntity oauth2UserDetailsEntity = new Oauth2UserDetailsEntity();
        oauth2UserDetailsEntity.setUsername(username);
        oauth2UserDetailsEntity = iOauth2UserDetailsRepository.findOne(Example.of(oauth2UserDetailsEntity)).orElseThrow(
                () -> new UsernameNotFoundException(Constants.ProveProveState.Sso_User_Non_Exist.getFace())
        );
        Oauth2UserDetailsBo oauth2UserDetailsBo = iConverter.convert(oauth2UserDetailsEntity, Oauth2UserDetailsBo.class);
        HttpServletRequest request = CommonHelper.getHttpServletRequest();
        // 预判验码是否命中
//        String code = request.getParameter(Constants.Form_Parameter_Code_Lowercase);
//        if(!StringUtils.hasText(code)) {
//            throw new BizException(Constants.BizStatus.Sso_User_Password_Incorrect);
//        }

        // 预判系统是否授权

        // 预判用户是否授权

        // 预判密码是否正确
        String encryptPassword = oauth2UserDetailsBo.getPassword();
        String password = request.getParameter(Constants.Form_Parameter_Name_Password);
        // 预判完全许可放行
        if (
                (
                        StringUtils.hasText(password) && passwordEncoder.matches(password, encryptPassword)
                )
                        ||
                        SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            return new org.springframework.security.core.userdetails.User(
                    username,
                    encryptPassword,
                    AuthorityUtils.NO_AUTHORITIES
            );
        }
        throw new BusinessException(Constants.ProveProveState.Sso_User_Password_Incorrect);
    }

    @Override
    public Long add(Oauth2UserDetailsBo oauth2UserDetailsBo) {
        Oauth2UserDetailsEntity oauth2UserDetailsEntity = new Oauth2UserDetailsEntity();
        oauth2UserDetailsEntity.setUsername(oauth2UserDetailsBo.getUsername());
        if (iOauth2UserDetailsRepository.exists(Example.of(oauth2UserDetailsEntity))) {
            throw new BusinessException(Constants.ProveProveState.Sso_User_Exist);
        }

        oauth2UserDetailsEntity = iConverter.convert(oauth2UserDetailsBo, Oauth2UserDetailsEntity.class);
        oauth2UserDetailsEntity.setPassword(passwordEncoder.encode(oauth2UserDetailsBo.getPassword()));
        return iOauth2UserDetailsRepository.save(oauth2UserDetailsEntity).getId();
    }

    @Override
    public void delete(Long id) {
        if (iOauth2UserDetailsRepository.existsById(id)) {
            iOauth2UserDetailsRepository.deleteById(id);
        }
    }

    @Override
    public Long update(Oauth2UserDetailsBo oauth2UserDetailsBo) {
        return this.add(oauth2UserDetailsBo);
    }

    @Override
    public Oauth2UserDetailsBo detail(Long id) {
        Oauth2UserDetailsEntity oauth2UserDetailsEntity = iOauth2UserDetailsRepository.findById(id).orElseThrow(
                () -> new BusinessException(Constants.ProveProveState.Sso_User_Non_Exist)
        );
        return iConverter.convert(oauth2UserDetailsEntity, Oauth2UserDetailsBo.class);
    }

    @Override
    public IPageableResponse<List<Oauth2UserDetailsVo>> list(Oauth2UserDetailsQo oauth2UserDetailsQry) {
        Specification<Oauth2UserDetailsEntity> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<PageableQo.ConditionComposition<?>> conditions = oauth2UserDetailsQry.getConditions();
            if (StringUtils.hasText(oauth2UserDetailsQry.getUsername())) {
                criteriaBuilder.equal(root.get("username"), oauth2UserDetailsQry.getUsername());
            }
            return criteriaBuilder.conjunction();
        };
        Page<Oauth2UserDetailsEntity> userDetailsEntities = iOauth2UserDetailsRepository.findAll(specification, PageRequest.of(
                oauth2UserDetailsQry.getCurrentIndex(),
                oauth2UserDetailsQry.getPageableSize(),
                StringUtils.hasText(oauth2UserDetailsQry.getDirection()) ? Sort.Direction.fromString(oauth2UserDetailsQry.getDirection()) : Sort.Direction.DESC,
                CollectionUtils.isEmpty(oauth2UserDetailsQry.getProperties()) ? String.join(",", oauth2UserDetailsQry.getProperties()) : "lastModifiedDate"
        ));
        return IPageableResponse.PageableResponse.response(userDetailsEntities.getTotalElements(), iConverter.convert(userDetailsEntities, Oauth2UserDetailsVo.class));
    }

}
