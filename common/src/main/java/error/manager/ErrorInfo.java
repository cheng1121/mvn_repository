package error.manager;

import com.cheng.common.error.api.IErrorCode;
import error.system.SystemErrorCodes;
import org.slf4j.helpers.MessageFormatter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @time: 2022/11/17 10:52
 * @author: licheng
 * @desc:
 */
public class ErrorInfo {
    //支持高并发更新与查询的哈希表
    static final Map<Integer,ErrorInfo> NO_PARAM_CODES_MAP = new ConcurrentHashMap<>();
    static final Map<String,ErrorInfo> ERROR_MSG_CODES_MAP = new ConcurrentHashMap<>();

    private final int code;

    private final String msg;


    public ErrorInfo(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ErrorInfo parse(String message){
        return ERROR_MSG_CODES_MAP.computeIfAbsent(message,it -> new ErrorInfo(SystemErrorCodes.SYSTEM_ERROR.getCode(),message));
    }

    public static ErrorInfo parse(IErrorCode errorCode){
         int code = errorCode.getCode();
         return NO_PARAM_CODES_MAP.computeIfAbsent(code,it -> new ErrorInfo(it,errorCode.getMsg()));
    }

    public static ErrorInfo parse(IErrorCode errorCode, Object ... args){
         String msg = MessageFormatter.arrayFormat(errorCode.getMsg(),args).getMessage();
         return new ErrorInfo(errorCode.getCode(),msg);

    }



    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "code=" + code +
                ", msg='" + msg;
    }
}
