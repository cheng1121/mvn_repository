package com.cheng.common.error.exception;


import com.cheng.common.error.api.IErrorCode;
import com.cheng.common.error.api.IProjectModule;
import com.cheng.common.error.manager.ErrorInfo;


public abstract class BaseRuntimeException extends RuntimeException implements IErrorCodeException{

    final ErrorInfo errorInfo;

    protected BaseRuntimeException(String message) {
        super(message);
        this.errorInfo = ErrorInfo.parse(message);
    }

    protected BaseRuntimeException(String message, Throwable cause) {
        super(message, cause);
        this.errorInfo = ErrorInfo.parse(message);
    }

    protected BaseRuntimeException(Throwable cause) {
        super(cause);
        this.errorInfo = ErrorInfo.parse(cause.getMessage());
    }

    protected BaseRuntimeException(ErrorInfo errorInfo) {
        super(errorInfo.toString());
        this.errorInfo = errorInfo;
    }

    protected BaseRuntimeException(IErrorCode errorCode) {
        this(ErrorInfo.parse(errorCode));
        IProjectModule.check(projectModule(), errorCode.projectModule());
    }

    protected BaseRuntimeException(IErrorCode errorCode, Object... args) {
        this(ErrorInfo.parse(errorCode, args));
        IProjectModule.check(projectModule(), errorCode.projectModule());
    }

    @Override
    public ErrorInfo getErrorInfo() {
        return errorInfo;
    }
}
