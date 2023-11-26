package com.later.horizon.work.bo;

import com.later.horizon.core.repository.entity.AbstractBoEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class Oauth2ClientTokenBo extends AbstractBoEntity implements Serializable {

    private static final long serialVersionUUID = 1L;

    private String tokenId;

    private byte[] token;

    private String authenticationId;

    private String username;

    private String clientId;

}
