package com.cheng.common.error.manager;

import com.cheng.common.error.api.IErrorCode;
import com.cheng.common.error.system.SystemErrorCodes;
import org.slf4j.helpers.MessageFormatter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class ErrorInfo {
    static final Map<Integer, ErrorInfo> NO_PARAM_CODES_MAP = new ConcurrentHashMap<>();
    static final Map<String, ErrorInfo> ERROR_MSG_CODES_MAP = new ConcurrentHashMap<>();
    /**
     * 错误码
     */
    private final int code;
    /**
     * 返回错误信息 英文
     */
    private final String msg;

    public ErrorInfo(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ErrorInfo parse(String message) {
        return ERROR_MSG_CODES_MAP.computeIfAbsent(message, it -> new ErrorInfo(SystemErrorCodes.SYSTEM_ERROR.getCode(), message));
    }

    public static ErrorInfo parse(IErrorCode errorCode) {
        int code = errorCode.getCode();
        return NO_PARAM_CODES_MAP.computeIfAbsent(code, it -> new ErrorInfo(it, errorCode.getMsg()));
    }

    public static ErrorInfo parse(IErrorCode errorCode, Object... args) {
        String msg = MessageFormatter.arrayFormat(errorCode.getMsg(), args).getMessage();
        return new ErrorInfo(errorCode.getCode(), msg);
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "code=" + code + ",msg=" + msg;
    }
}
