package error.system;

import com.cheng.common.error.api.IErrorCode;
import com.cheng.common.error.manager.ErrorManager;
import com.cheng.common.error.system.SystemIProjectModule;

/**
 * @time: 2022/11/17 10:59
 * @author: licheng
 * @desc: 系统错误码
 */
public enum SystemErrorCodes implements IErrorCode {

    SUCCESS(0, "ok"),
    SYSTEM_ERROR(1, "system error");

    private final int nodeNum;

    private final String msg;

    SystemErrorCodes(int nodeNum, String msg) {
        this.nodeNum = nodeNum;
        this.msg = msg;
        //注册系统模块
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
