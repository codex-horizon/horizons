package com.later.horizon.common.restful;

import lombok.Data;

import java.io.Serializable;

public interface IPageableResponse<T> {

    long getTotal();

    T getList();

    @Data
    class PageableResponse<T> implements IPageableResponse<T>, Serializable {

        private static final long serialVersionUUID = 1L;

        private long total;

        private T list;

        private PageableResponse(final Long total, final T list) {
            this.total = total;
            this.list = list;
        }

        public static <T> PageableResponse<T> response(final Long total, final T list) {
            return new PageableResponse<>(total, list);
        }

    }
}
