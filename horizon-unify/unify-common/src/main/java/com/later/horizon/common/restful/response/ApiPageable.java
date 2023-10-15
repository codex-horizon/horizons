package com.later.horizon.common.restful.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class ApiPageable<T> implements IPageable<T>, Serializable {

    private long total;

    private T list;

    private ApiPageable(final Long total, final T list) {
        this.total = total;
        this.list = list;
    }

    public static <T> ApiPageable<T> response(final Long total, final T list) {
        return new ApiPageable<>(total, list);
    }

}
