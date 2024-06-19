package com.later.horizon.common.exception;

import com.later.horizon.common.constants.Constants;

public class SystemError extends Error {


    public SystemError(final Constants.ProveStatus proveState) {
        super(proveState.getMessage());
    }

    public SystemError(final Constants.ProveStatus proveState, final Throwable cause) {
        super(proveState.getMessage(), cause);
    }

    public SystemError(Throwable cause) {
        super(cause);
    }

    protected SystemError(String message, Throwable cause,
                          boolean enableSuppression,
                          boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
