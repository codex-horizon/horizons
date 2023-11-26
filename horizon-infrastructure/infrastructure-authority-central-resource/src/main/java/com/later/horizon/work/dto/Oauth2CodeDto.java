package com.later.horizon.work.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class Oauth2CodeDto implements Serializable {

    private static final long serialVersionUUID = 1L;

    private String code;

    private byte[] authentication;

}
