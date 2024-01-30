package com.later.horizon.work.service.impl;

import com.alibaba.fastjson2.JSON;
import com.later.horizon.common.constants.Constants;
import com.later.horizon.common.converter.IConverter;
import com.later.horizon.common.exception.BusinessException;
import com.later.horizon.common.restful.IPageableResponse;
import com.later.horizon.common.restful.PageableQo;
import com.later.horizon.work.bo.Oauth2ClientDetailsBo;
import com.later.horizon.work.entity.Oauth2ClientDetailsEntity;
import com.later.horizon.work.qry.Oauth2ClientDetailsQo;
import com.later.horizon.work.repository.IOauth2ClientDetailsRepository;
import com.later.horizon.work.service.IOauth2ClientDetailsService;
import com.later.horizon.work.vo.Oauth2ClientDetailsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class Oauth2ClientDetailsService implements IOauth2ClientDetailsService {

    private final IConverter iConverter;

    private final IOauth2ClientDetailsRepository iOauth2ClientDetailsRepository;

    private final PasswordEncoder passwordEncoder;


    Oauth2ClientDetailsService(final IConverter iConverter,
                               final IOauth2ClientDetailsRepository iOauth2ClientDetailsRepository,
                               final PasswordEncoder passwordEncoder) {
        this.iConverter = iConverter;
        this.iOauth2ClientDetailsRepository = iOauth2ClientDetailsRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long add(Oauth2ClientDetailsBo oauth2ClientDetailsBo) {
        Oauth2ClientDetailsEntity oauth2ClientDetailsEntity = new Oauth2ClientDetailsEntity();
        oauth2ClientDetailsEntity.setClientId(oauth2ClientDetailsBo.getClientId());
        if (iOauth2ClientDetailsRepository.exists(Example.of(oauth2ClientDetailsEntity))) {
            oauth2ClientDetailsEntity = iOauth2ClientDetailsRepository.findOne(Example.of(oauth2ClientDetailsEntity)).orElseThrow(
                    () -> new BusinessException(Constants.ProveProveState.Sso_Client_Exist_Multiple)
            );
            return oauth2ClientDetailsEntity.getId();
        } else {
            oauth2ClientDetailsEntity = iConverter.convert(oauth2ClientDetailsBo, Oauth2ClientDetailsEntity.class);
            return this.save(oauth2ClientDetailsEntity, oauth2ClientDetailsBo);
        }
    }

    @Override
    public void delete(Long id) {
        if (iOauth2ClientDetailsRepository.existsById(id)) {
            iOauth2ClientDetailsRepository.deleteById(id);
        }
    }

    @Override
    public Long update(Oauth2ClientDetailsBo oauth2ClientDetailsBo) {
        Optional.ofNullable(oauth2ClientDetailsBo).map(Oauth2ClientDetailsBo::getId).orElseThrow(
                () -> new BusinessException(Constants.ProveProveState.Sso_Client_Non_Connected)
        );
        Oauth2ClientDetailsEntity oauth2ClientDetailsEntity = iOauth2ClientDetailsRepository.findById(oauth2ClientDetailsBo.getId()).orElseThrow(
                () -> new BusinessException(Constants.ProveProveState.Sso_Client_Non_Connected)
        );
        return this.save(oauth2ClientDetailsEntity, oauth2ClientDetailsBo);
    }

    @Override
    public Oauth2ClientDetailsBo detail(Long id) {
        Oauth2ClientDetailsEntity oauth2ClientDetailsEntity = iOauth2ClientDetailsRepository.findById(id).orElseThrow(
                () -> new BusinessException(Constants.ProveProveState.Sso_Client_Non_Connected)
        );
        return iConverter.convert(oauth2ClientDetailsEntity, Oauth2ClientDetailsBo.class);
    }

    @Override
    public IPageableResponse<List<Oauth2ClientDetailsVo>> list(Oauth2ClientDetailsQo oauth2ClientDetailsQry) {
        Specification<Oauth2ClientDetailsEntity> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<PageableQo.ConditionComposition<?>> conditions = oauth2ClientDetailsQry.getConditions();
            if (StringUtils.hasText(oauth2ClientDetailsQry.getClientId())) {
                criteriaBuilder.equal(root.get("clientId"), oauth2ClientDetailsQry.getClientId());
            }
            return criteriaBuilder.conjunction();
        };
        Page<Oauth2ClientDetailsEntity> clientDetailsEntities = iOauth2ClientDetailsRepository.findAll(specification, PageRequest.of(
                oauth2ClientDetailsQry.getCurrentIndex(),
                oauth2ClientDetailsQry.getPageableSize(),
                StringUtils.hasText(oauth2ClientDetailsQry.getDirection()) ? Sort.Direction.fromString(oauth2ClientDetailsQry.getDirection()) : Sort.Direction.DESC,
                CollectionUtils.isEmpty(oauth2ClientDetailsQry.getProperties()) ? String.join(",", oauth2ClientDetailsQry.getProperties()) : "lastModifiedDate"
        ));
        return IPageableResponse.PageableResponse.response(clientDetailsEntities.getTotalElements(), iConverter.convert(clientDetailsEntities, Oauth2ClientDetailsVo.class));
    }

    private Long save(Oauth2ClientDetailsEntity oauth2ClientDetailsEntity, Oauth2ClientDetailsBo oauth2ClientDetailsBo) {
        oauth2ClientDetailsEntity.setClientSecret(passwordEncoder.encode(oauth2ClientDetailsEntity.getClientSecret()));
        oauth2ClientDetailsEntity.setResourceIds(String.join(",", oauth2ClientDetailsBo.getResourceIds()));
        oauth2ClientDetailsEntity.setScope(String.join(",", oauth2ClientDetailsBo.getScope()));
        oauth2ClientDetailsEntity.setAuthorizedGrantTypes(String.join(",", oauth2ClientDetailsBo.getAuthorizedGrantTypes()));
        oauth2ClientDetailsEntity.setAuthorities(String.join(",", oauth2ClientDetailsBo.getAuthorities()));
        oauth2ClientDetailsEntity.setAdditionalInformation(JSON.toJSONString(oauth2ClientDetailsBo.getAdditionalInformation()));
        oauth2ClientDetailsEntity.setState(Constants.ProveProveState.Data_Status_Available.getState());
        return iOauth2ClientDetailsRepository.save(oauth2ClientDetailsEntity).getId();
    }
}
