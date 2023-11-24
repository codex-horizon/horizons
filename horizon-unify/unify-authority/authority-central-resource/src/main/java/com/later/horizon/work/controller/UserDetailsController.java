package com.later.horizon.work.controller;

import com.later.horizon.common.converter.IConverter;
import com.later.horizon.common.restful.IPageable;
import com.later.horizon.common.restful.IResult;
import com.later.horizon.common.validated.GroupValidator;
import com.later.horizon.work.bo.UserDetailsBo;
import com.later.horizon.work.dto.UserDetailsDto;
import com.later.horizon.work.qry.UserDetailsQry;
import com.later.horizon.work.service.IUserDetailsService;
import com.later.horizon.work.vo.UserDetailsVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userDetails")
public class UserDetailsController {

    private final IUserDetailsService iUserDetailsService;

    private final IConverter iConverter;

    UserDetailsController(final IUserDetailsService iUserDetailsService,
                          final IConverter iConverter) {
        this.iUserDetailsService = iUserDetailsService;
        this.iConverter = iConverter;
    }

    @RequestMapping(name = "新增用户", path = "/add", method = RequestMethod.POST)
    IResult<Long> add(@RequestBody @Validated(GroupValidator.Create.class) UserDetailsDto userDetailsDto) {
        UserDetailsBo userDetailsBo = iConverter.convert(userDetailsDto, UserDetailsBo.class);
        return IResult.Result.succeeded(iUserDetailsService.add(userDetailsBo));
    }

    @RequestMapping(name = "删除用户", path = "/delete/{id:\\d+}", method = RequestMethod.POST)
    IResult<String> delete(@PathVariable("id") Long id) {
        iUserDetailsService.delete(id);
        return IResult.Result.succeeded();
    }

    @RequestMapping(name = "修改用户", path = "/update", method = RequestMethod.POST)
    IResult<Long> update(@RequestBody @Validated(GroupValidator.Modify.class) UserDetailsDto userDetailsDto) {
        UserDetailsBo userDetailsBo = iConverter.convert(userDetailsDto, UserDetailsBo.class);
        return IResult.Result.succeeded(iUserDetailsService.update(userDetailsBo));
    }

    @RequestMapping(name = "详情用户", path = "/detail/{id:\\d+}", method = RequestMethod.POST)
    IResult<UserDetailsVo> detail(@PathVariable("id") Long id) {
        UserDetailsBo userDetailsBo = iUserDetailsService.detail(id);
        return IResult.Result.succeeded(iConverter.convert(userDetailsBo, UserDetailsVo.class));
    }

    @RequestMapping(name = "列表用户", path = "/list", method = RequestMethod.POST)
    IResult<IPageable<List<UserDetailsVo>>> list(@RequestBody UserDetailsQry userDetailsQry) {
        return IResult.Result.succeeded(iUserDetailsService.list(userDetailsQry));
    }

}
