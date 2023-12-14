package com.later.horizon.work.dto;

import com.later.horizon.common.validated.GroupValidator;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Map;

@Data
public class Oauth2ClientDetailsDto implements Serializable {

    private static final long serialVersionUUID = 1L;

    @NotBlank(message = "client_id 空", groups = {
            GroupValidator.Create.class,
            GroupValidator.Modify.class
    })
    private String clientId;

    @NotNull(message = "resource_ids 空", groups = {
            GroupValidator.Create.class,
            GroupValidator.Modify.class
    })
    private String[] resourceIds;

    @NotBlank(message = "client_secret 空", groups = {
            GroupValidator.Create.class,
            GroupValidator.Modify.class
    })
    private String clientSecret;

    @NotNull(message = "scope 空", groups = {
            GroupValidator.Create.class,
            GroupValidator.Modify.class
    })
    private String[] scope;

    @NotNull(message = "authorized_grant_types 空", groups = {
            GroupValidator.Create.class,
            GroupValidator.Modify.class
    })
    private String[] authorizedGrantTypes;

    @NotBlank(message = "web_server_redirect_uri 空", groups = {
            GroupValidator.Create.class,
            GroupValidator.Modify.class
    })
    private String webServerRedirectUri;

    @NotNull(message = "authorities 空", groups = {
            GroupValidator.Create.class,
            GroupValidator.Modify.class
    })
    private String[] authorities;

    @NotNull(message = "access_token_validity 空", groups = {
            GroupValidator.Create.class,
            GroupValidator.Modify.class
    })
    private Integer accessTokenValidity;

    @NotNull(message = "refresh_token_validity 空", groups = {
            GroupValidator.Create.class,
            GroupValidator.Modify.class
    })
    private Integer refreshTokenValidity;

    @NotNull(message = "additional_information 空", groups = {
            GroupValidator.Create.class,
            GroupValidator.Modify.class
    })
    private Map<String, Object> additionalInformation;

    @NotNull(message = "autoapprove 空", groups = {
            GroupValidator.Create.class,
            GroupValidator.Modify.class
    })
    private Boolean autoApprove;

    @NotNull(message = "id 空", groups = GroupValidator.Modify.class)
    private Long id;
    // private String id;

}

