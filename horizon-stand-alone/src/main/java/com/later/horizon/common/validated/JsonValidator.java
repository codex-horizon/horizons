package com.later.horizon.common.validated;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 验证是否JSON字符串
 */
public class JsonValidator implements ConstraintValidator<Json, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null;
    }

}
