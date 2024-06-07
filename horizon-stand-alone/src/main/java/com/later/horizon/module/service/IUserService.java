package com.later.horizon.module.service;

import com.later.horizon.common.restful.IPageable;
import com.later.horizon.module.bo.UserBo;
import com.later.horizon.module.qry.UserQry;
import com.later.horizon.module.vo.UserVo;

import java.util.List;

public interface IUserService {

    IPageable<List<UserVo>> pageable(UserQry userQry);

    Long add(UserBo userBo);

    Long update(UserBo userBo);

    Boolean delete(Long id);

    Object authentication(UserBo userBo);

}
