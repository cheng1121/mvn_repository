package com.cheng.common.error.exception;


import com.cheng.common.error.api.IErrorCode;
import com.cheng.common.error.api.IProjectModule;
import com.cheng.common.error.manager.ErrorInfo;


public abstract class BaseException extends Exception implements IErrorCodeException {

    final ErrorInfo errorInfo;

    protected BaseException(String message) {
        super(message);
        this.errorInfo = ErrorInfo.parse(message);
    }

    protected BaseException(String message, Throwable cause) {
        super(message, cause);
        this.errorInfo = ErrorInfo.parse(message);
    }

    protected BaseException(Throwable cause) {
        super(cause);
        this.errorInfo = ErrorInfo.parse(cause.getMessage());
    }

    protected BaseException(ErrorInfo errorInfo) {
        super(errorInfo.toString());
        this.errorInfo = errorInfo;
    }

    protected BaseException(IErrorCode errorCode) {
        this(ErrorInfo.parse(errorCode));
        IProjectModule.check(projectModule(), errorCode.projectModule());
    }

    protected BaseException(IErrorCode errorCode, Object... args) {
        this(ErrorInfo.parse(errorCode, args));
        IProjectModule.check(projectModule(), errorCode.projectModule());
    }

    @Override
    public ErrorInfo getErrorInfo() {
        return errorInfo;
    }
}
