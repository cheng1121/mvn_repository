package response;

import com.cheng.common.error.api.IErrorCode;
import error.manager.ErrorInfo;
import error.system.SystemErrorCodes;

/**
 * @time: 2022/11/17 11:26
 * @author: licheng
 * @desc: 请求返回接口封装
 */
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


}
