package com.cheng.common.error.system;

import com.cheng.common.error.api.IErrorCode;
import com.cheng.common.error.manager.ErrorManager;

/**
 * 基础错误码定义
 *
 */
public enum SystemErrorCodes implements IErrorCode {

    SUCCESS(0, "ok"),
    SYSTEM_ERROR(1, "system com.cheng.common.error");

    private final int nodeNum;
    private final String msg;

    SystemErrorCodes(int nodeNum, String msg) {
        this.nodeNum = nodeNum;
        this.msg = msg;
        ErrorManager.register(SystemIProjectModule.INSTANCE, this);
    }

    @Override
    public int getNodeNum() {
        return nodeNum;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
