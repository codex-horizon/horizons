package com.later.horizon.common.restful;

import lombok.Data;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Data
public class PageableQry implements Serializable {

    private int currentIndex = 0;

    private int pageableSize = 10;

    private List<SimpleConditions<?>> conditions = Collections.emptyList();

    private String direction;

    private List<String> properties;

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex > 0 ? --currentIndex : currentIndex;
    }

    @Data
    public static class SimpleConditions<T> {

        private String name;

        private String calc;

        private T value;

        private String logic;

    }
}
