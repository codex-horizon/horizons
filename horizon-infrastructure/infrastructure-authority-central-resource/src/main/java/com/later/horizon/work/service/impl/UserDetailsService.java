package com.later.horizon.work.service.impl;

import com.later.horizon.common.constants.Constants;
import com.later.horizon.common.converter.IConverter;
import com.later.horizon.common.exception.BizException;
import com.later.horizon.common.restful.IPageable;
import com.later.horizon.common.restful.PageableQry;
import com.later.horizon.work.bo.UserDetailsBo;
import com.later.horizon.work.entity.UserDetailsEntity;
import com.later.horizon.work.qry.UserDetailsQry;
import com.later.horizon.work.repository.IUserDetailsRepository;
import com.later.horizon.work.service.IUserDetailsService;
import com.later.horizon.work.vo.UserDetailsVo;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

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

    @Override
    public Long add(UserDetailsBo userDetailsBo) {
        UserDetailsEntity userDetailsEntity = new UserDetailsEntity();
        userDetailsEntity.setUsername(userDetailsBo.getUsername());
        if (iUserDetailsRepository.exists(Example.of(userDetailsEntity))) {
            throw new BizException(Constants.BizStatus.Sso_User_Exists);
        }

        userDetailsEntity = iConverter.convert(userDetailsBo, UserDetailsEntity.class);
        userDetailsEntity.setPassword(passwordEncoder.encode(userDetailsBo.getPassword()));
        return iUserDetailsRepository.save(userDetailsEntity).getId();
    }

    @Override
    public void delete(Long id) {
        if (iUserDetailsRepository.existsById(id)) {
            iUserDetailsRepository.deleteById(id);
        }
    }

    @Override
    public Long update(UserDetailsBo userDetailsBo) {
        return this.add(userDetailsBo);
    }

    @Override
    public UserDetailsBo detail(Long id) {
        UserDetailsEntity userDetailsEntity = iUserDetailsRepository.findById(id).orElseThrow(() -> new BizException(Constants.BizStatus.Sso_User_Details_Not_Found));
        return iConverter.convert(userDetailsEntity, UserDetailsBo.class);
    }

    @Override
    public IPageable<List<UserDetailsVo>> list(UserDetailsQry userDetailsQry) {
        Specification<UserDetailsEntity> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<PageableQry.SimpleConditions<?>> conditions = userDetailsQry.getConditions();
            if (StringUtils.hasText(userDetailsQry.getUsername())) {
                criteriaBuilder.equal(root.get("username"), userDetailsQry.getUsername());
            }
            return criteriaBuilder.conjunction();
        };
        Page<UserDetailsEntity> userDetailsEntities = iUserDetailsRepository.findAll(specification, PageRequest.of(
                userDetailsQry.getCurrentIndex(),
                userDetailsQry.getPageableSize(),
                StringUtils.hasText(userDetailsQry.getDirection()) ? Sort.Direction.fromString(userDetailsQry.getDirection()) : Sort.Direction.DESC,
                CollectionUtils.isEmpty(userDetailsQry.getProperties()) ? String.join(",", userDetailsQry.getProperties()) : "lastModifiedDate"
        ));
        return IPageable.Pageable.response(userDetailsEntities.getTotalElements(), iConverter.convert(userDetailsEntities, UserDetailsVo.class));
    }

}
