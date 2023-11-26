package com.later.horizon.work.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class Oauth2RefreshTokenDto implements Serializable {

    private static final long serialVersionUUID = 1L;

    private String tokenId;

    private byte[] token;

    private byte[] authentication;

}
