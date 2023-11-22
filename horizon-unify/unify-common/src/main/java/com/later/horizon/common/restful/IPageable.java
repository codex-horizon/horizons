package com.later.horizon.common.restful;

import lombok.Data;

import java.io.Serializable;

public interface IPageable<T> {

    long getTotal();

    T getList();

    @Data
    class ApiPageable<T> implements IPageable<T>, Serializable {

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
}
