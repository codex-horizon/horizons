package com.later.horizon.common.restful;

import lombok.Data;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Data
public class PageableQry implements Serializable {

    private static final long serialVersionUUID = 1L;

    private int currentIndex = 0;

    private int pageableSize = 10;

    private List<ConditionComposition<?>> conditions = Collections.emptyList();

    private String direction;

    private List<String> properties = Collections.emptyList();

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex > 0 ? --currentIndex : currentIndex;
    }

    @Data
    public static class ConditionComposition<T> {

        private String name;

        /**
         * >(大于)、<(小于)、=(等于)、<>(不等)、like(模糊)、and(与)、or(或)
         */
        private String logic;

        private T value;

    }
}
