package com.later.horizon.module.controller;

import com.later.horizon.common.converter.IConverter;
import com.later.horizon.common.restful.IPageable;
import com.later.horizon.common.restful.IRestful;
import com.later.horizon.common.validated.GroupValidator;
import com.later.horizon.module.bo.UserBo;
import com.later.horizon.module.dto.UserDto;
import com.later.horizon.module.qry.UserQry;
import com.later.horizon.module.service.IUserService;
import com.later.horizon.module.vo.UserVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(name = "用户（账户）管理控制器", path = "/user")
public class UserController {

    private final IUserService iUserService;

    private final IConverter iConverter;

    UserController(final IUserService iUserService,
                   final IConverter iConverter) {
        this.iUserService = iUserService;
        this.iConverter = iConverter;
    }

    @RequestMapping(name = "用户分页", path = "/pageable", method = RequestMethod.POST)
    public IRestful<IPageable<List<UserVo>>> pageable(@RequestBody UserQry userQry) {
        return IRestful.Result.succeeded(iUserService.pageable(userQry));
    }

    @RequestMapping(name = "用户添加", path = "/add", method = RequestMethod.POST)
    public IRestful<Long> add(@RequestBody @Validated(GroupValidator.Create.class) UserDto userDto) {
        return IRestful.Result.succeeded(iUserService.add(iConverter.convert(userDto, UserBo.class)));
    }

    @RequestMapping(name = "用户更新", path = "/update", method = RequestMethod.POST)
    public IRestful<Long> update(@RequestBody @Validated(GroupValidator.Modify.class) UserDto userDto) {
        return IRestful.Result.succeeded(iUserService.update(iConverter.convert(userDto, UserBo.class)));
    }

    @RequestMapping(name = "用户删除", path = "/delete/{id}", method = RequestMethod.POST)
    public IRestful<Boolean> delete(@PathVariable("id")Long id) {
        return IRestful.Result.succeeded(iUserService.delete(id));
    }

    @RequestMapping(name = "用户认证", path = "/authentication", method = RequestMethod.POST)
    public IRestful<?> login(@RequestBody @Validated(GroupValidator.Authentication.class) UserDto userDto) {
        return IRestful.Result.succeeded(iUserService.authentication(iConverter.convert(userDto, UserBo.class)));
    }

    @GetMapping(name = "实验性", path = "/exp/{id}")
    public IRestful<Long> exp(@PathVariable("id") Long id) {
        return IRestful.Result.succeeded(id);
    }
}
