package com.later.horizon.work.vo;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class Oauth2ApprovalsVo implements Serializable {

    private static final long serialVersionUUID = 1L;

    private String userId;

    private String clientId;

    private String scope;

    private String status;

    private Timestamp expiresAt;

    private Timestamp lastModifiedDate;

}

