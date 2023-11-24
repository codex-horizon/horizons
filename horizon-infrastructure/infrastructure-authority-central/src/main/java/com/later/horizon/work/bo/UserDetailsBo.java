package com.later.horizon.work.bo;

import com.later.horizon.core.repository.entity.AbstractBoEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserDetailsBo extends AbstractBoEntity implements Serializable {

    private static final long serialVersionUUID = 1L;

    private String username;

    private String password;

}
