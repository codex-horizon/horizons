package com.later.horizon.work.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class Oauth2ClientDetailsVo implements Serializable {

    private static final long serialVersionUUID = 1L;

    private String clientId;

    private String[] resourceIds;

    private String clientSecret;

    private String[] scope;

    private String[] authorizedGrantTypes;

    private String webServerRedirectUri;

    private String[] authorities;

    private Long accessTokenValidity;

    private Long refreshTokenValidity;

    private Map<String, Object> additionalInformation;

    private Boolean autoApprove;

}

