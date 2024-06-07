package com.later.horizon.common.restful;

import lombok.Data;

import java.io.Serializable;

public interface IResponse {

    String getApplicationName();

    void setApplicationName(String applicationName);

    String getTraceId();

    void setTraceId(String seqNo);

    Object getBody();

    void setBody(Object o);

    @Data
    class Response implements IResponse, Serializable {

        private static final long serialVersionUUID = 1L;

        private String applicationName;

        private String traceId;

        private Object body;

        protected Response(final String applicationName, final String traceId, final Object body) {
            this.applicationName = applicationName;
            this.traceId = traceId;
            this.body = body;
        }

        public static Response restful(final String applicationName, final String traceId, final Object body) {
            return new Response(applicationName, traceId, body);
        }

    }

}

