package com.later.horizon.work.dto;

import com.later.horizon.common.validated.GroupValidator;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class Oauth2UserDetailsDto implements Serializable {

    private static final long serialVersionUUID = 1L;

    @NotBlank(message = "username 空", groups = {
            GroupValidator.Create.class,
            GroupValidator.Modify.class
    })
    private String username;

    @NotBlank(message = "password 空", groups = {
            GroupValidator.Create.class,
            GroupValidator.Modify.class
    })
    private String password;

}
