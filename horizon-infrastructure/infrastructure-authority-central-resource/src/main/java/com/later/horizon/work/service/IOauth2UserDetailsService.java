package com.later.horizon.work.service;

import com.later.horizon.common.restful.IPageable;
import com.later.horizon.work.bo.Oauth2UserDetailsBo;
import com.later.horizon.work.qry.Oauth2UserDetailsQry;
import com.later.horizon.work.vo.Oauth2UserDetailsVo;

import java.util.List;

public interface IOauth2UserDetailsService {

    Long add(Oauth2UserDetailsBo oauth2UserDetailsBo);

    void delete(Long id);

    Long update(Oauth2UserDetailsBo oauth2UserDetailsBo);

    Oauth2UserDetailsBo detail(Long id);

    IPageable<List<Oauth2UserDetailsVo>> list(Oauth2UserDetailsQry oauth2UserDetailsQry);
}
