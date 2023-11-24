package com.later.horizon.work.service;

import com.later.horizon.common.restful.IPageable;
import com.later.horizon.work.bo.UserDetailsBo;
import com.later.horizon.work.qry.UserDetailsQry;
import com.later.horizon.work.vo.UserDetailsVo;

import java.util.List;

public interface IUserDetailsService {

    Long add(UserDetailsBo userDetailsBo);

    void delete(Long id);

    Long update(UserDetailsBo userDetailsBo);

    UserDetailsBo detail(Long id);

    IPageable<List<UserDetailsVo>> list(UserDetailsQry userDetailsQry);
}
