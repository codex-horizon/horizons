package com.later.horizon.module.dto;

import com.later.horizon.common.validated.GroupValidator;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class UserDto implements Serializable {

    private static final long serialVersionUUID = 1L;

    @NotNull(message = "id 空", groups = {
            GroupValidator.Modify.class
    })
    private Long id;

    @NotBlank(message = "username 空", groups = {
            GroupValidator.Create.class,
            GroupValidator.Authentication.class
    })
    private String username;

    @NotBlank(message = "username 空", groups = {
            GroupValidator.Create.class,
            GroupValidator.Modify.class,
            GroupValidator.Authentication.class
    })
    private String password;

    private Long roleId;

    private List<Long> comicIds;
}
