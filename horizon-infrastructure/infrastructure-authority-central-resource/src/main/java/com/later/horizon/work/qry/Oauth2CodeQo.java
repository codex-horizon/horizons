package com.later.horizon.work.qry;

import com.later.horizon.common.restful.PageableQo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Oauth2CodeQo extends PageableQo {

    private String code;

}
