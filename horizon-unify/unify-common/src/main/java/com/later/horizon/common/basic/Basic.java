package com.later.horizon.common.basic;

import com.later.horizon.common.converter.Converter;
import com.later.horizon.common.converter.IConverter;

public abstract class Basic {

    public final IConverter iConverter;

    Basic() {
        iConverter = new Converter();
    }

}
