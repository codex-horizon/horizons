package com.later.horizon.common.restful.response;

public interface IPageable<T> {

    long getTotal();

    T getList();

}
