package com.later.horizon.module.service.impl;


import com.later.horizon.common.constants.Constants;
import com.later.horizon.common.converter.IConverter;
import com.later.horizon.common.exception.BusinessException;
import com.later.horizon.common.restful.IPageable;
import com.later.horizon.module.bo.UserBo;
import com.later.horizon.module.entity.UserEntity;
import com.later.horizon.module.qry.UserQry;
import com.later.horizon.module.repository.IUserRepository;
import com.later.horizon.module.service.IUserService;
import com.later.horizon.module.vo.UserVo;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.*;
import java.util.List;

@Service
public class UserService implements IUserService {

    private final IConverter iConverter;

    private final IUserRepository iUserRepository;

    UserService(final IConverter iConverter,
                final IUserRepository iUserRepository) {
        this.iConverter = iConverter;
        this.iUserRepository = iUserRepository;
    }

    /**
     * 1.Root<T> root :代表要查询的对象，也就是实体类型，实体类型好比sql语句中的from后的表。传入实体类型后，会被CriteriaQuery的父类AbstractQuery.from将实体类型传入。
     * 2.CriteriaQuery<?> criteriaQuery ：这是一个面向对象查询，代表的是Specific的顶层查询对象，它包含查询的各个部分：select，from，where，group by, order by等，
     * 不过它是一个面向对象的查询方式，只对实体类型，嵌入式类型的Criteria查询起作用。
     * 3.CriteriaBuilder criteriaBuilder:用来构建CriteriaQuery的构建器对象Predicate（谓语），即：equals, gt,lt,in,like等一个简单或者复杂的谓语类型，相当于条件或者多条件集合。
     * 4.Predicate：就是多条件查询中的条件，可以通过List<Predicate> 实现多个条件操作。
     */
    @Override
    public IPageable<List<UserVo>> pageable(UserQry userQry) {
        /*Specification<UserEntity> specification = (root, criteriaQuery, criteriaBuilder) -> criteriaQuery.where(
                criteriaBuilder.like(root.get("username"), "%".concat(userQry.getUsername()).concat("%"))
        ).getRestriction();*/
        Page<UserEntity> pageable = iUserRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(userQry.getUsername())) {
                predicates.add(criteriaBuilder.like(root.get("username"), "%" + userQry.getUsername() + "%"));
            }
            if (predicates.isEmpty()) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        }, PageRequest.of(
                userQry.getCurrentIndex(),
                userQry.getPageableSize(),
                Sort.Direction.DESC,
                userQry.getProperties()
        ));
        return IPageable.Pageable.response(pageable.getTotalElements(), iConverter.convert(pageable, UserVo.class));
    }

    @Override
    public Long add(UserBo userBo) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userBo.getUsername());
        if (iUserRepository.exists(Example.of(userEntity))) {
            throw new BusinessException(Constants.ProveProveState.User_Name_Exists);
        }
        userBo.setState(Constants.ProveProveState.Data_Status_Available.getState());
        return iUserRepository.save(iConverter.convert(userBo, UserEntity.class)).getId();
    }

    @Override
    public Long update(UserBo userBo) {
        UserEntity userEntity = iUserRepository.findById(userBo.getId()).orElseThrow(() -> new BusinessException(Constants.ProveProveState.User_Non_Existent));
        userEntity.setPassword(userBo.getPassword());
        return iUserRepository.save(userEntity).getId();
    }

    @Override
    public Boolean delete(Long id) {
        UserEntity userEntity = iUserRepository.findById(id).orElseThrow(() -> new BusinessException(Constants.ProveProveState.Data_Status_Removed));
        userEntity.setState(Constants.ProveProveState.Data_Status_Removed.getState());
        iUserRepository.save(userEntity);
        return Boolean.TRUE;
    }

    @Override
    public Object authentication(UserBo userBo) {
        return null;
    }
}