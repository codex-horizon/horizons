package com.later.horizon.common.validated;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = JsonValidator.class)
public @interface Json {

    String message();

    // groups 和 payload 这两个parameter 必须包含,不然会报错
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
