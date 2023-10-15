package com.later.horizon.common.restful.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class ApiPageable implements Serializable {

    private int index = 0;

    private int size = 10;

    public void setIndex(int index) {
        if (index > 0) {
            --index;
        }
        this.index = index;
    }

}
