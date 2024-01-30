package com.later.horizon.work.service;

import com.later.horizon.common.restful.IPageableResponse;
import com.later.horizon.work.bo.Oauth2ClientDetailsBo;
import com.later.horizon.work.qry.Oauth2ClientDetailsQo;
import com.later.horizon.work.vo.Oauth2ClientDetailsVo;

import java.util.List;

public interface IOauth2ClientDetailsService {

    Long add(Oauth2ClientDetailsBo oauth2ClientDetailsBo);

    void delete(Long id);

    Long update(Oauth2ClientDetailsBo oauth2ClientDetailsBo);

    Oauth2ClientDetailsBo detail(Long id);

    IPageableResponse<List<Oauth2ClientDetailsVo>> list(Oauth2ClientDetailsQo oauth2ClientDetailsQry);

}
