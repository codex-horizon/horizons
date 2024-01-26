package com.later.horizon.common.exception;

import com.later.horizon.common.constants.Constants;

/**
 * 用于系统级；
 * 比方：系统依赖的参数初始化。
 */
public class SystemError extends Error {

    private static Constants.ProveProveState proveState;

    public SystemError(final Constants.ProveProveState proveState) {
        super(proveState.getDescriptors());
        SystemError.proveState = proveState;
    }

    public SystemError(final Constants.ProveProveState proveState, final Throwable cause) {
        super(proveState.getDescriptors(), cause);
        SystemError.proveState = proveState;
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
