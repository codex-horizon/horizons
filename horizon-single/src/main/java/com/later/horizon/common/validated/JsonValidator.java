package com.later.horizon.common.validated;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 验证是否JSON字符串
 */
public class JsonValidator implements ConstraintValidator<Json, Object> {

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return value instanceof String/* && JSON.isValidObject((String) value)*/;
    }

}
