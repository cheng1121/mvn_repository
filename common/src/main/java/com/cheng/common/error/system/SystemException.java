package com.cheng.common.error.system;

import com.cheng.common.error.api.IErrorCode;
import com.cheng.common.error.api.IProjectModule;
import com.cheng.common.error.exception.BaseException;
import com.cheng.common.error.manager.ErrorInfo;

public class SystemException extends BaseException {
    public SystemException(String message) {
        super(message);
    }

    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public SystemException(Throwable cause) {
        super(cause);
    }

    public SystemException(ErrorInfo errorInfo) {
        super(errorInfo);
    }

    public SystemException(IErrorCode errorCode) {
        super(errorCode);
    }

    public SystemException(IErrorCode errorCode, Object... args) {
        super(errorCode, args);
    }

    @Override
    public IProjectModule projectModule() {
        return SystemIProjectModule.INSTANCE;
    }
}
