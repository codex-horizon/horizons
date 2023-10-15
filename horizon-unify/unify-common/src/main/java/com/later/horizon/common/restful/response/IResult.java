package com.later.horizon.common.restful.response;

public interface IResult<T> {

    String getCode();

    String getMessage();

    T getData();

}
