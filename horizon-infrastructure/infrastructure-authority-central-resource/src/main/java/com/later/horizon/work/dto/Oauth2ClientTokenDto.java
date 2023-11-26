package com.later.horizon.work.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class Oauth2ClientTokenDto implements Serializable {

    private static final long serialVersionUUID = 1L;

    private String tokenId;

    private byte[] token;

    private String authenticationId;

    private String username;

    private String clientId;

}
