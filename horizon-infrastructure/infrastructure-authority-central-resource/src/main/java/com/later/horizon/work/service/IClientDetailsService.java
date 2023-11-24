package com.later.horizon.work.service;

import com.later.horizon.common.restful.IPageable;
import com.later.horizon.work.bo.ClientDetailsBo;
import com.later.horizon.work.qry.ClientDetailsQry;
import com.later.horizon.work.vo.ClientDetailsVo;

import java.util.List;

public interface IClientDetailsService {

    Long add(ClientDetailsBo clientDetailsBo);

    void delete(Long id);

    Long update(ClientDetailsBo clientDetailsBo);

    ClientDetailsBo detail(Long id);

    IPageable<List<ClientDetailsVo>> list(ClientDetailsQry clientDetailsQry);

}
