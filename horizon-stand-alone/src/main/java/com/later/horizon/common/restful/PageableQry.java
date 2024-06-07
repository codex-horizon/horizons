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

    private String[] properties = new String[]{"lastModifiedDate"};

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex > 0 ? --currentIndex : currentIndex;
    }

    @Data
    public static class ConditionComposition<T> {

        // 字段名
        private String name;

        // 字段条件：>(大于)、<(小于)、=>(大于等于)、<=(小于等于)、=(等于)、<>(不等)、like(模糊)、and(与)、or(或)
        private String logic;

        // 字段值
        private T value;

    }
}
