package com.later.horizon.common.restful;

import com.later.horizon.common.constants.Constants;
import lombok.Data;

import java.io.Serializable;

public interface IResponse<T> {

    String getCode();

    String getFace();

    String getDescriptors();

    T getData();

    @Data
    class Result<T> implements IResponse<T>, Serializable {

        private static final long serialVersionUUID = 1L;

        private String code;

        private String face;

        private String descriptors;

        private T data;

        private Result(final Constants.ProveProveState proveProveState) {
            this.code = proveProveState.getCode();
            this.face = proveProveState.getFace();
            this.descriptors = proveProveState.getDescriptors();
        }

        private Result(final Constants.ProveProveState proveProveState, final String descriptors) {
            this.code = proveProveState.getCode();
            this.face = proveProveState.getFace();
            this.descriptors = descriptors;
        }

        private Result(final Constants.ProveProveState proveProveState, final String face, final String descriptors) {
            this.code = proveProveState.getCode();
            this.face = face;
            this.descriptors = descriptors;
        }

        private Result(final Constants.ProveProveState proveProveState, final String descriptors, final T data) {
            this.code = proveProveState.getCode();
            this.face = proveProveState.getFace();
            this.descriptors = descriptors;
            this.data = data;
        }

        private static <T> IResponse<T> restful(final Constants.ProveProveState proveProveState) {
            return new Result<>(proveProveState);
        }

        private static <T> IResponse<T> restful(final Constants.ProveProveState proveProveState, final String descriptors) {
            return new Result<>(proveProveState, descriptors);
        }

        private static <T> IResponse<T> restful(final Constants.ProveProveState proveProveState, final String face, final String descriptors) {
            return new Result<>(proveProveState, face, descriptors);
        }

        private static <T> IResponse<T> restful(final Constants.ProveProveState proveProveState, final String descriptors, final T data) {
            return new Result<>(proveProveState, descriptors, data);
        }

        public static <T> IResponse<T> succeeded() {
            return restful(Constants.ProveProveState.Business_Processing_Status_Succeeded);
        }

        public static <T> IResponse<T> succeeded(final Constants.ProveProveState proveProveState) {
            return restful(proveProveState);
        }

        public static <T> IResponse<T> succeeded(final T data) {
            return restful(Constants.ProveProveState.Business_Processing_Status_Succeeded, Constants.ProveProveState.Business_Processing_Status_Succeeded.getDescriptors(), data);
        }

        public static <T> IResponse<T> failed() {
            return restful(Constants.ProveProveState.Business_Processing_Status_Failed);
        }


        public static <T> IResponse<T> failed(final Constants.ProveProveState proveProveState) {
            return restful(proveProveState);
        }

        public static <T> IResponse<T> failed(final String descriptors) {
            return restful(Constants.ProveProveState.Business_Processing_Status_Failed, descriptors);
        }

        public static <T> IResponse<T> failed(final String face, final String descriptors) {
            return restful(Constants.ProveProveState.Business_Processing_Status_Failed, face, descriptors);
        }

    }
}
