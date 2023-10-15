package com.later.horizon.common.restful.response;

import com.later.horizon.common.constants.Constants;
import lombok.Data;

import java.io.Serializable;

@Data
public class ApiResult<T> implements IResult<T>, Serializable {

    private String code;

    private String message;

    private T data;

    private ApiResult(final Constants.BizResponseStatus responseStatus) {
        this.code = responseStatus.getCode();
        this.message = responseStatus.getMessage();
    }

    private ApiResult(final Constants.BizResponseStatus responseStatus, final String message) {
        this.code = responseStatus.getCode();
        this.message = message;
    }

    private ApiResult(final Constants.BizResponseStatus responseStatus, final String message, final T data) {
        this.code = responseStatus.getCode();
        this.message = message;
        this.data = data;
    }

    private static <T> IResult<T> restful(final Constants.BizResponseStatus responseStatus) {
        return new ApiResult<>(responseStatus);
    }

    private static <T> IResult<T> restful(final Constants.BizResponseStatus responseStatus, final String message) {
        return new ApiResult<>(responseStatus, message);
    }

    private static <T> IResult<T> restful(final Constants.BizResponseStatus responseStatus, final String message, final T data) {
        return new ApiResult<>(responseStatus, message, data);
    }

    public static <T> IResult<T> failed() {
        return restful(Constants.BizResponseStatus.Biz_Failed_Response);
    }

    public static <T> IResult<T> failed(final Constants.BizResponseStatus responseStatus) {
        return restful(responseStatus);
    }
    public static <T> IResult<T> failed(final String message) {
        return restful(Constants.BizResponseStatus.Biz_Failed_Response, message);
    }

    public static <T> IResult<T> succeeded() {
        return restful(Constants.BizResponseStatus.Biz_Ok_Response);
    }

    public static <T> IResult<T> succeeded(final T data) {
        return restful(Constants.BizResponseStatus.Biz_Ok_Response, Constants.BizResponseStatus.Biz_Ok_Response.getMessage(), data);
    }

}

