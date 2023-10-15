package com.later.horizon.common.helper;

import com.later.horizon.common.constants.Constants;
import com.later.horizon.common.exception.BizException;
import org.springframework.util.StringUtils;

public class AssertHelper {
    public static void isTrue(boolean expression, Constants.BizStatus bizStatus) {
        if (!expression) {
            throw new BizException(bizStatus);
        }
    }

    public static void isNotNull(Object object, Constants.BizStatus bizStatus) {
        if (object == null) {
            throw new BizException(bizStatus);
        }
    }

    public static void hasText(String text, Constants.BizStatus bizStatus) {
        if (!StringUtils.hasText(text)) {
            throw new BizException(bizStatus);
        }
    }

}
