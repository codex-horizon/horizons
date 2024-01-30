package com.later.horizon.work.controller;

import com.later.horizon.common.converter.IConverter;
import com.later.horizon.common.restful.IPageableResponse;
import com.later.horizon.common.restful.IResponse;
import com.later.horizon.common.validated.GroupValidator;
import com.later.horizon.work.bo.Oauth2ClientDetailsBo;
import com.later.horizon.work.dto.Oauth2ClientDetailsDto;
import com.later.horizon.work.qry.Oauth2ClientDetailsQo;
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
    IResponse<Long> add(@RequestBody @Validated(GroupValidator.Create.class) Oauth2ClientDetailsDto oauth2ClientDetailsDto) {
        Oauth2ClientDetailsBo oauth2ClientDetailsBo = iConverter.convert(oauth2ClientDetailsDto, Oauth2ClientDetailsBo.class);
        return IResponse.Result.succeeded(iOauth2ClientDetailsService.add(oauth2ClientDetailsBo));
    }

    @RequestMapping(name = "删除客户端", path = "/delete/{id:\\d+}", method = RequestMethod.POST)
    IResponse<String> delete(@PathVariable("id") Long id) {
        iOauth2ClientDetailsService.delete(id);
        return IResponse.Result.succeeded();
    }

    @RequestMapping(name = "修改客户端", path = "/update", method = RequestMethod.POST)
    IResponse<Long> update(@RequestBody @Validated(GroupValidator.Modify.class) Oauth2ClientDetailsDto oauth2ClientDetailsDto) {
        Oauth2ClientDetailsBo oauth2ClientDetailsBo = iConverter.convert(oauth2ClientDetailsDto, Oauth2ClientDetailsBo.class);
        return IResponse.Result.succeeded(iOauth2ClientDetailsService.update(oauth2ClientDetailsBo));
    }

    @RequestMapping(name = "详情客户端", path = "/detail/{id:\\d+}", method = RequestMethod.POST)
    IResponse<Oauth2ClientDetailsVo> detail(@PathVariable("id") Long id) {
        Oauth2ClientDetailsBo oauth2ClientDetailsBo = iOauth2ClientDetailsService.detail(id);
        return IResponse.Result.succeeded(iConverter.convert(oauth2ClientDetailsBo, Oauth2ClientDetailsVo.class));
    }

    @RequestMapping(name = "列表客户端", path = "/list", method = RequestMethod.POST)
    IResponse<IPageableResponse<List<Oauth2ClientDetailsVo>>> list(@RequestBody Oauth2ClientDetailsQo oauth2ClientDetailsQry) {
        return IResponse.Result.succeeded(iOauth2ClientDetailsService.list(oauth2ClientDetailsQry));
    }

}
