package com.later.horizon.common.restful;

import lombok.Data;

import java.io.Serializable;

public interface IGlobalResponse {

    String getApplicationName();

    void setApplicationName(String applicationName);

    String getTraceId();

    void setTraceId(String seqNo);

    Object getBody();

    void setBody(Object o);


    @Data
    class ApiGlobalResult implements IGlobalResponse, Serializable {

        private String applicationName;

        private String traceId;

        private Object body;

        protected ApiGlobalResult(final String applicationName, final String traceId, final Object body) {
            this.applicationName = applicationName;
            this.traceId = traceId;
            this.body = body;
        }

        public static ApiGlobalResult restful(final String applicationName, final String traceId, final Object body) {
            return new ApiGlobalResult(applicationName, traceId, body);
        }

    }

}

