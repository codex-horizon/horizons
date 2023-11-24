package com.later.horizon.work.bo;

import com.later.horizon.core.repository.entity.AbstractBoEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class ClientDetailsBo extends AbstractBoEntity implements Serializable {

    private static final long serialVersionUUID = 1L;

    private String clientId;

    private String[] resourceIds;

    private String clientSecret;

    private String[] scope;

    private String[] authorizedGrantTypes;

    private String webServerRedirectUri;

    private String[] authorities;

    private Integer accessTokenValidity;

    private Integer refreshTokenValidity;

    private Map<String, String> additionalInformation;

    private Boolean autoApprove;

}
