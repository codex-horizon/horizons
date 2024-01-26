package com.later.horizon.common.exception;

import com.later.horizon.common.constants.Constants;

public class BusinessException extends RuntimeException {

    private static Constants.ProveProveState proveState;

    public BusinessException(final Constants.ProveProveState proveState) {
        super(proveState.getDescriptors());
        BusinessException.proveState = proveState;
    }


    public BusinessException(final Constants.ProveProveState proveState, final Throwable cause) {
        super(proveState.getDescriptors(), cause);
        BusinessException.proveState = proveState;
    }

    /**
     * 尽量勿用
     */
    public BusinessException(final Throwable cause) {
        super(cause);
    }

    /**
     * 尽量勿用
     */
    public BusinessException(final Constants.ProveProveState proveState,
                             final Throwable cause,
                             final boolean enableSuppression,
                             final boolean writableStackTrace) {
        super(proveState.getDescriptors(), cause, enableSuppression, writableStackTrace);
    }

}

