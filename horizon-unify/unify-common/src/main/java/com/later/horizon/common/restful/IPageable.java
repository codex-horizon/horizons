package com.later.horizon.common.restful;

import lombok.Data;

import java.io.Serializable;

public interface IPageable<T> {

    long getTotal();

    T getList();

    @Data
    class Pageable<T> implements IPageable<T>, Serializable {

        private long total;

        private T list;

        private Pageable(final Long total, final T list) {
            this.total = total;
            this.list = list;
        }

        public static <T> Pageable<T> response(final Long total, final T list) {
            return new Pageable<>(total, list);
        }

    }
}
