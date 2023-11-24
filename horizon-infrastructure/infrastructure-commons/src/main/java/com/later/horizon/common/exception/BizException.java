package com.later.horizon.common.exception;

import com.later.horizon.common.constants.Constants;

public class BizException extends RuntimeException {

    private String code;

    private String message;

    public BizException(final Constants.BizStatus bizStatus) {
        super(bizStatus.getMessage());
        this.code = bizStatus.getCode();
        this.message = bizStatus.getMessage();
    }

    public BizException(final Constants.BizResponseStatus bizResponseStatus) {
        super(bizResponseStatus.getMessage());
        this.code = bizResponseStatus.getCode();
        this.message = bizResponseStatus.getMessage();
    }

    public BizException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public BizException(final Throwable cause) {
        super(cause);
    }

    public BizException(final String message, final Throwable cause,
                        final boolean enableSuppression,
                        final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}

