package com.later.horizon.work.vo;

import com.later.horizon.core.repository.entity.AbstractVoEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class Oauth2RefreshTokenVo extends AbstractVoEntity implements Serializable {

    private static final long serialVersionUUID = 1L;

    private String tokenId;

    private byte[] token;

    private byte[] authentication;

}
