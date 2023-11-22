package com.later.horizon.work.controller;

import com.later.horizon.common.converter.IConverter;
import com.later.horizon.common.restful.IPageable;
import com.later.horizon.common.restful.IResult;
import com.later.horizon.common.validated.GroupValidator;
import com.later.horizon.work.bo.ClientDetailsBo;
import com.later.horizon.work.dto.ClientDetailsDto;
import com.later.horizon.work.qry.ClientDetailsQry;
import com.later.horizon.work.service.IClientDetailsService;
import com.later.horizon.work.vo.ClientDetailsVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientDetails")
public class ClientDetailsController {

    private final IClientDetailsService iClientDetailsService;

    private final IConverter iConverter;

    ClientDetailsController(final IClientDetailsService iClientDetailsService,
                            final IConverter iConverter) {
        this.iClientDetailsService = iClientDetailsService;
        this.iConverter = iConverter;
    }

    @RequestMapping(name = "新增客户端", path = "/add", method = RequestMethod.POST)
    IResult<Long> add(@RequestBody @Validated(GroupValidator.Create.class) ClientDetailsDto clientDetailsDto) {
        ClientDetailsBo clientDetailsBo = iConverter.convert(clientDetailsDto, ClientDetailsBo.class);
        return IResult.Result.succeeded(iClientDetailsService.add(clientDetailsBo));
    }

    @RequestMapping(name = "删除客户端", path = "/delete/{id:\\d+}", method = RequestMethod.POST)
    IResult<String> delete(@PathVariable("id") Long id) {
        iClientDetailsService.delete(id);
        return IResult.Result.succeeded();
    }

    @RequestMapping(name = "修改客户端", path = "/update", method = RequestMethod.POST)
    IResult<Long> update(@RequestBody @Validated(GroupValidator.Modify.class) ClientDetailsDto clientDetailsDto) {
        ClientDetailsBo clientDetailsBo = iConverter.convert(clientDetailsDto, ClientDetailsBo.class);
        return IResult.Result.succeeded(iClientDetailsService.update(clientDetailsBo));
    }

    @RequestMapping(name = "详情客户端", path = "/detail/{id:\\d+}", method = RequestMethod.POST)
    IResult<ClientDetailsVo> detail(@PathVariable("id") Long id) {
        ClientDetailsBo clientDetailsBo = iClientDetailsService.detail(id);
        return IResult.Result.succeeded(iConverter.convert(clientDetailsBo, ClientDetailsVo.class));
    }

    @RequestMapping(name = "列表客户端", path = "/list", method = RequestMethod.POST)
    IResult<IPageable<List<ClientDetailsVo>>> list(@RequestBody ClientDetailsQry clientDetailsQry) {
        return IResult.Result.succeeded(iClientDetailsService.list(clientDetailsQry));
    }

}
