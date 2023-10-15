package com.later.horizon.common.restful.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class ApiGlobalResult implements IGlobalResponse, Serializable {

    private String applicationName;

    private String traceId;

    private Object body;

    private ApiGlobalResult(final String applicationName, final String traceId, final Object body) {
        this.applicationName = applicationName;
        this.traceId = traceId;
        this.body = body;
    }

    public static ApiGlobalResult restful(final String applicationName, final String traceId, final Object body) {
        return new ApiGlobalResult(applicationName, traceId, body);
    }

}
