package com.later.horizon.work.qry;

import com.later.horizon.common.restful.PageableQo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Oauth2ClientTokenQo extends PageableQo {

    private String clientId;

}
