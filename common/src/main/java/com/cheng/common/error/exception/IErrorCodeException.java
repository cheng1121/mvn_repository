package com.cheng.common.error.exception;

import com.cheng.common.error.api.IProjectModule;
import com.cheng.common.error.manager.ErrorInfo;


public interface IErrorCodeException {
    /**
     * 错误信息
     */
    ErrorInfo getErrorInfo();

    /**
     * 服务+模块信息
     */
    IProjectModule projectModule();
}
