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
    class GlobalResponse implements IGlobalResponse, Serializable {

        private static final long serialVersionUUID = 1L;

        private String applicationName;

        private String traceId;

        private Object body;

        protected GlobalResponse(final String applicationName, final String traceId, final Object body) {
            this.applicationName = applicationName;
            this.traceId = traceId;
            this.body = body;
        }

        public static GlobalResponse restful(final String applicationName, final String traceId, final Object body) {
            return new GlobalResponse(applicationName, traceId, body);
        }

    }

}

