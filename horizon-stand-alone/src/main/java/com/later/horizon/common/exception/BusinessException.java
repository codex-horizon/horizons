package com.later.horizon.common.exception;

import com.later.horizon.common.constants.Constants;

public class BusinessException extends RuntimeException {


    public BusinessException(final Constants.ProveStatus proveState) {
        super(proveState.getMessage());
    }


    public BusinessException(final Constants.ProveStatus proveState, final Throwable cause) {
        super(proveState.getMessage(), cause);
    }

    public BusinessException(final Throwable cause) {
        super(cause);
    }

    public BusinessException(final Constants.ProveStatus proveState,
                             final Throwable cause,
                             final boolean enableSuppression,
                             final boolean writableStackTrace) {
        super(proveState.getMessage(), cause, enableSuppression, writableStackTrace);
    }

}

