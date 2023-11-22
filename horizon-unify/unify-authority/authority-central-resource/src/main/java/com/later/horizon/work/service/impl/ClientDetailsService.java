package com.later.horizon.work.service.impl;

import com.alibaba.fastjson2.JSON;
import com.later.horizon.common.constants.Constants;
import com.later.horizon.common.converter.IConverter;
import com.later.horizon.common.exception.BizException;
import com.later.horizon.common.restful.PageableQry;
import com.later.horizon.common.restful.IPageable;
import com.later.horizon.work.bo.ClientDetailsBo;
import com.later.horizon.work.entity.ClientDetailsEntity;
import com.later.horizon.work.qry.ClientDetailsQry;
import com.later.horizon.work.repository.IClientDetailsRepository;
import com.later.horizon.work.service.IClientDetailsService;
import com.later.horizon.work.vo.ClientDetailsVo;
import lombok.extern.slf4j.Slf4j;
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
public class ClientDetailsService implements IClientDetailsService {

    private final IConverter iConverter;

    private final IClientDetailsRepository iClientDetailsRepository;

    private final PasswordEncoder passwordEncoder;


    ClientDetailsService(final IConverter iConverter,
                         final IClientDetailsRepository iClientDetailsRepository,
                         final PasswordEncoder passwordEncoder) {
        this.iConverter = iConverter;
        this.iClientDetailsRepository = iClientDetailsRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long add(ClientDetailsBo clientDetailsBo) {
        ClientDetailsEntity clientDetailsEntity = iConverter.convert(clientDetailsBo, ClientDetailsEntity.class);
        return this.save(clientDetailsEntity, clientDetailsBo);
    }

    @Override
    public void delete(Long id) {
        if (iClientDetailsRepository.existsById(id)) {
            iClientDetailsRepository.deleteById(id);
        }
    }

    @Override
    public Long update(ClientDetailsBo clientDetailsBo) {
        Optional.ofNullable(clientDetailsBo).map(ClientDetailsBo::getId).orElseThrow(() -> new BizException(Constants.BizStatus.Sso_Client_Details_Not_Found));
        ClientDetailsEntity clientDetailsEntity = iClientDetailsRepository.findById(clientDetailsBo.getId()).orElseThrow(() -> new BizException(Constants.BizStatus.Sso_Client_Details_Not_Found));
        return this.save(clientDetailsEntity, clientDetailsBo);
    }

    @Override
    public ClientDetailsBo detail(Long id) {
        ClientDetailsEntity clientDetailsEntity = iClientDetailsRepository.findById(id).orElseThrow(() -> new BizException(Constants.BizStatus.Sso_Client_Details_Not_Found));
        return iConverter.convert(clientDetailsEntity, ClientDetailsBo.class);
    }

    @Override
    public IPageable<List<ClientDetailsVo>> list(ClientDetailsQry clientDetailsQry) {
        Specification<ClientDetailsEntity> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<PageableQry.SimpleConditions<?>> conditions = clientDetailsQry.getConditions();
            if (StringUtils.hasText(clientDetailsQry.getClientId())) {
                criteriaBuilder.equal(root.get("clientId"), clientDetailsQry.getClientId());
            }
            return criteriaBuilder.conjunction();
        };
        Page<ClientDetailsEntity> accountEntities = iClientDetailsRepository.findAll(specification, PageRequest.of(
                clientDetailsQry.getCurrentIndex(),
                clientDetailsQry.getPageableSize(),
                StringUtils.hasText(clientDetailsQry.getDirection()) ? Sort.Direction.fromString(clientDetailsQry.getDirection()) : Sort.Direction.DESC,
                CollectionUtils.isEmpty(clientDetailsQry.getProperties()) ? String.join(",", clientDetailsQry.getProperties()) : "lastModifiedDate"
        ));
        return IPageable.ApiPageable.response(accountEntities.getTotalElements(), iConverter.convert(accountEntities, ClientDetailsVo.class));
    }

    private Long save(ClientDetailsEntity clientDetailsEntity, ClientDetailsBo clientDetailsBo) {
        clientDetailsEntity.setClientSecret(passwordEncoder.encode(clientDetailsEntity.getClientSecret()));
        clientDetailsEntity.setResourceIds(String.join(",", clientDetailsBo.getResourceIds()));
        clientDetailsEntity.setScope(String.join(",", clientDetailsBo.getScope()));
        clientDetailsEntity.setAuthorizedGrantTypes(String.join(",", clientDetailsBo.getAuthorizedGrantTypes()));
        clientDetailsEntity.setAuthorities(String.join(",", clientDetailsBo.getAuthorities()));
        clientDetailsEntity.setAdditionalInformation(JSON.toJSONString(clientDetailsBo.getAdditionalInformation()));
        return iClientDetailsRepository.save(clientDetailsEntity).getId();
    }
}
