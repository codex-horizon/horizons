package com.later.horizon.work.controller;

import com.later.horizon.common.converter.IConverter;
import com.later.horizon.common.restful.IPageableResponse;
import com.later.horizon.common.restful.IResponse;
import com.later.horizon.common.validated.GroupValidator;
import com.later.horizon.work.bo.Oauth2UserDetailsBo;
import com.later.horizon.work.dto.Oauth2UserDetailsDto;
import com.later.horizon.work.qry.Oauth2UserDetailsQo;
import com.later.horizon.work.service.IOauth2UserDetailsService;
import com.later.horizon.work.vo.Oauth2UserDetailsVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/oauth2UserDetails")
public class Oauth2UserDetailsController {

    private final IOauth2UserDetailsService iOauth2UserDetailsService;

    private final IConverter iConverter;

    Oauth2UserDetailsController(final IOauth2UserDetailsService iOauth2UserDetailsService,
                                final IConverter iConverter) {
        this.iOauth2UserDetailsService = iOauth2UserDetailsService;
        this.iConverter = iConverter;
    }

    @RequestMapping(name = "新增用户", path = "/add", method = RequestMethod.POST)
    IResponse<Long> add(@RequestBody @Validated(GroupValidator.Create.class) Oauth2UserDetailsDto oauth2UserDetailsDto) {
        Oauth2UserDetailsBo oauth2UserDetailsBo = iConverter.convert(oauth2UserDetailsDto, Oauth2UserDetailsBo.class);
        return IResponse.Result.succeeded(iOauth2UserDetailsService.add(oauth2UserDetailsBo));
    }

    @RequestMapping(name = "删除用户", path = "/delete/{id:\\d+}", method = RequestMethod.POST)
    IResponse<String> delete(@PathVariable("id") Long id) {
        iOauth2UserDetailsService.delete(id);
        return IResponse.Result.succeeded();
    }

    @RequestMapping(name = "修改用户", path = "/update", method = RequestMethod.POST)
    IResponse<Long> update(@RequestBody @Validated(GroupValidator.Modify.class) Oauth2UserDetailsDto oauth2UserDetailsDto) {
        Oauth2UserDetailsBo oauth2UserDetailsBo = iConverter.convert(oauth2UserDetailsDto, Oauth2UserDetailsBo.class);
        return IResponse.Result.succeeded(iOauth2UserDetailsService.update(oauth2UserDetailsBo));
    }

    @RequestMapping(name = "详情用户", path = "/detail/{id:\\d+}", method = RequestMethod.POST)
    IResponse<Oauth2UserDetailsVo> detail(@PathVariable("id") Long id) {
        Oauth2UserDetailsBo oauth2UserDetailsBo = iOauth2UserDetailsService.detail(id);
        return IResponse.Result.succeeded(iConverter.convert(oauth2UserDetailsBo, Oauth2UserDetailsVo.class));
    }

    @RequestMapping(name = "列表用户", path = "/list", method = RequestMethod.POST)
    IResponse<IPageableResponse<List<Oauth2UserDetailsVo>>> list(@RequestBody Oauth2UserDetailsQo oauth2UserDetailsQry) {
        return IResponse.Result.succeeded(iOauth2UserDetailsService.list(oauth2UserDetailsQry));
    }

}
