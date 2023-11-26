package com.later.horizon.work.controller;

import com.later.horizon.common.converter.IConverter;
import com.later.horizon.common.restful.IPageable;
import com.later.horizon.common.restful.IResult;
import com.later.horizon.common.validated.GroupValidator;
import com.later.horizon.work.bo.Oauth2ClientDetailsBo;
import com.later.horizon.work.dto.Oauth2ClientDetailsDto;
import com.later.horizon.work.qry.Oauth2ClientDetailsQry;
import com.later.horizon.work.service.IOauth2ClientDetailsService;
import com.later.horizon.work.vo.Oauth2ClientDetailsVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/oauth2ClientDetails")
public class Oauth2ClientDetailsController {

    private final IOauth2ClientDetailsService iOauth2ClientDetailsService;

    private final IConverter iConverter;

    Oauth2ClientDetailsController(final IOauth2ClientDetailsService iOauth2ClientDetailsService,
                                  final IConverter iConverter) {
        this.iOauth2ClientDetailsService = iOauth2ClientDetailsService;
        this.iConverter = iConverter;
    }

    @RequestMapping(name = "新增客户端", path = "/add", method = RequestMethod.POST)
    IResult<Long> add(@RequestBody @Validated(GroupValidator.Create.class) Oauth2ClientDetailsDto oauth2ClientDetailsDto) {
        Oauth2ClientDetailsBo oauth2ClientDetailsBo = iConverter.convert(oauth2ClientDetailsDto, Oauth2ClientDetailsBo.class);
        return IResult.Result.succeeded(iOauth2ClientDetailsService.add(oauth2ClientDetailsBo));
    }

    @RequestMapping(name = "删除客户端", path = "/delete/{id:\\d+}", method = RequestMethod.POST)
    IResult<String> delete(@PathVariable("id") Long id) {
        iOauth2ClientDetailsService.delete(id);
        return IResult.Result.succeeded();
    }

    @RequestMapping(name = "修改客户端", path = "/update", method = RequestMethod.POST)
    IResult<Long> update(@RequestBody @Validated(GroupValidator.Modify.class) Oauth2ClientDetailsDto oauth2ClientDetailsDto) {
        Oauth2ClientDetailsBo oauth2ClientDetailsBo = iConverter.convert(oauth2ClientDetailsDto, Oauth2ClientDetailsBo.class);
        return IResult.Result.succeeded(iOauth2ClientDetailsService.update(oauth2ClientDetailsBo));
    }

    @RequestMapping(name = "详情客户端", path = "/detail/{id:\\d+}", method = RequestMethod.POST)
    IResult<Oauth2ClientDetailsVo> detail(@PathVariable("id") Long id) {
        Oauth2ClientDetailsBo oauth2ClientDetailsBo = iOauth2ClientDetailsService.detail(id);
        return IResult.Result.succeeded(iConverter.convert(oauth2ClientDetailsBo, Oauth2ClientDetailsVo.class));
    }

    @RequestMapping(name = "列表客户端", path = "/list", method = RequestMethod.POST)
    IResult<IPageable<List<Oauth2ClientDetailsVo>>> list(@RequestBody Oauth2ClientDetailsQry oauth2ClientDetailsQry) {
        return IResult.Result.succeeded(iOauth2ClientDetailsService.list(oauth2ClientDetailsQry));
    }

}
