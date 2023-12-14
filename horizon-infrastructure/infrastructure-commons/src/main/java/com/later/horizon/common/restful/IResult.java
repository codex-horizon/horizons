package com.later.horizon.common.restful;

import com.later.horizon.common.constants.Constants;
import lombok.Data;

import java.io.Serializable;

public interface IResult<T> {

    String getCode();

    String getMessage();

    T getData();

    @Data
    class Result<T> implements IResult<T>, Serializable {

        private static final long serialVersionUUID = 1L;

        private String code;

        private String message;

        private T data;

        private Result(final Constants.BizResponseState bizResponseState) {
            this.code = bizResponseState.getCode();
            this.message = bizResponseState.getMessage();
        }

        private Result(final Constants.BizResponseState bizResponseState, final String message) {
            this.code = bizResponseState.getCode();
            this.message = message;
        }

        private Result(final Constants.BizResponseState bizResponseState, final String message, final T data) {
            this.code = bizResponseState.getCode();
            this.message = message;
            this.data = data;
        }

        private static <T> IResult<T> restful(final Constants.BizResponseState bizResponseState) {
            return new Result<>(bizResponseState);
        }

        private static <T> IResult<T> restful(final Constants.BizResponseState bizResponseState, final String message) {
            return new Result<>(bizResponseState, message);
        }

        private static <T> IResult<T> restful(final Constants.BizResponseState bizResponseState, final String message, final T data) {
            return new Result<>(bizResponseState, message, data);
        }

        public static <T> IResult<T> failed() {
            return restful(Constants.BizResponseState.Biz_Failed_Response);
        }

        public static <T> IResult<T> failed(final Constants.BizResponseState bizResponseState) {
            return restful(bizResponseState);
        }
        public static <T> IResult<T> failed(final String message) {
            return restful(Constants.BizResponseState.Biz_Failed_Response, message);
        }

        public static <T> IResult<T> succeeded() {
            return restful(Constants.BizResponseState.Biz_Ok_Response);
        }

        public static <T> IResult<T> succeeded(final T data) {
            return restful(Constants.BizResponseState.Biz_Ok_Response, Constants.BizResponseState.Biz_Ok_Response.getMessage(), data);
        }

    }
}
