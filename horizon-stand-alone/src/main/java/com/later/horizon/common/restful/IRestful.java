package com.later.horizon.common.restful;

import com.later.horizon.common.constants.Constants;
import lombok.Data;

import java.io.Serializable;

public interface IRestful<T> {

    String getCode();

    String getMessage();

    T getData();

    @Data
    class Result<T> implements IRestful<T>, Serializable {

        private static final long serialVersionUUID = 1L;

        private String code;

        private String message;

        private T data;

        private Result(final Constants.ProveStatus proveState) {
            this.code = proveState.getCode();
            this.message = proveState.getMessage();
        }

        private Result(final Constants.ProveStatus proveState, final T data) {
            this.code = proveState.getCode();
            this.message = proveState.getMessage();
            this.data = data;
        }

        private static <T> IRestful<T> restful(final Constants.ProveStatus proveState) {
            return new Result<>(proveState);
        }

        private static <T> IRestful<T> restful(final Constants.ProveStatus proveState, final T data) {
            return new Result<>(proveState, data);
        }

        private static <T> IRestful<T> restful(final Constants.ProveStatus proveState, final String message, final T data) {
            return new Result<>(proveState, data);
        }

        public static <T> IRestful<T> succeeded() {
            return restful(Constants.ProveStatus.Succeeded);
        }

        public static <T> IRestful<T> succeeded(final T data) {
            return restful(Constants.ProveStatus.Succeeded, data);
        }

        public static <T> IRestful<T> succeeded(final String message, final T data) {
            return restful(Constants.ProveStatus.Succeeded, message, data);
        }

        public static <T> IRestful<T> failed() {
            return restful(Constants.ProveStatus.Failed);
        }

        public static <T> IRestful<T> failed(String message) {
            return restful(Constants.ProveStatus.Failed);
        }

    }
}
