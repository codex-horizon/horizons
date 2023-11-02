package com.later.horizon.work.dto;

import com.later.horizon.common.validated.GroupValidator;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
@Data
public class UserDto implements Serializable {

    private static final long serialVersionUUID = 1L;

    @NotBlank(message = "ID 空", groups = GroupValidator.Modify.class)
    private String id;

    @NotBlank(message = "用户名 空", groups = {
            GroupValidator.Login.class,
            GroupValidator.Create.class,
            GroupValidator.Modify.class
    })
    private String username;

    @NotBlank(message = "密码 空", groups = {
            GroupValidator.Login.class,
            GroupValidator.Create.class,
            GroupValidator.Modify.class
    })
    private String password;

}
