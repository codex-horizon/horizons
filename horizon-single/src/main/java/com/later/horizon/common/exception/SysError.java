package com.later.horizon.common.exception;

public class SysError extends Error {

    public SysError() {
        super();
    }

    public SysError(String message) {
        super(message);
    }

    public SysError(String message, Throwable cause) {
        super(message, cause);
    }

    public SysError(Throwable cause) {
        super(cause);
    }

    protected SysError(String message, Throwable cause,
                       boolean enableSuppression,
                       boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
