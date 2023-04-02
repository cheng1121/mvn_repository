package com.cheng.common.response;


import com.cheng.common.error.api.IErrorCode;
import com.cheng.common.error.manager.ErrorInfo;
import com.cheng.common.error.system.SystemErrorCodes;

public class Result<T> extends ErrorInfo {

    private T data;

    public Result(int code, String msg) {
        super(code, msg);
    }


    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>(SystemErrorCodes.SUCCESS.getCode(), SystemErrorCodes.SUCCESS.getMsg());
        result.data = data;
        return result;
    }

    public static <T> Result<T> error(IErrorCode errorCode) {
        return new Result<>(errorCode.getCode(), errorCode.getMsg());
    }

    public static Result<String> error(Integer code, String msg) {
        return new Result<>(code, msg);
    }

    public T getData() {
        return data;
    }
}
