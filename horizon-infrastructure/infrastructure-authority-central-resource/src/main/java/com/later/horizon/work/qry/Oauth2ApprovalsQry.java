package com.later.horizon.work.qry;

import com.later.horizon.common.restful.PageableQry;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Oauth2ApprovalsQry extends PageableQry {

    private String userId;

}
