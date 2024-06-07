package com.later.horizon.module.qry;

import com.later.horizon.common.restful.PageableQry;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserQry extends PageableQry {

    private String username;

}
