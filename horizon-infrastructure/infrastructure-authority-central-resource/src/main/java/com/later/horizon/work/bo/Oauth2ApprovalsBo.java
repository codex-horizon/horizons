package com.later.horizon.work.bo;

import com.later.horizon.core.repository.entity.AbstractBoEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Data
public class Oauth2ApprovalsBo extends AbstractBoEntity implements Serializable {

    private static final long serialVersionUUID = 1L;

    private String userId;

    private String clientId;

    private String scope;

    private String status;

    private Timestamp expiresAt;

    private Timestamp lastModifiedDate;

}
