package error.exception;

import com.cheng.common.error.api.IErrorCode;
import com.cheng.common.error.api.IProjectModule;
import error.manager.ErrorInfo;

/**
 * @time: 2022/11/17 11:09
 * @author: licheng
 * @desc:
 */
public abstract class BaseException extends Exception implements IErrorCodeException {

    final ErrorInfo errorInfo;

    public BaseException(String message) {
        super(message);
        this.errorInfo = ErrorInfo.parse(message);
    }

    public BaseException(Throwable cause) {
        super(cause);
        this.errorInfo = ErrorInfo.parse(cause.getMessage());
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
        this.errorInfo = ErrorInfo.parse(message);
    }

    public BaseException(ErrorInfo errorInfo) {
        super(errorInfo.toString());
        this.errorInfo = errorInfo;
    }


    public BaseException(IErrorCode errorCode) {
        this(ErrorInfo.parse(errorCode));
        IProjectModule.check(projectModule(), errorCode.projectModule());
    }

    public BaseException(IErrorCode errorCode, Object ...args){
        this(ErrorInfo.parse(errorCode,args));
        IProjectModule.check(projectModule(), errorCode.projectModule());
    }



    @Override
    public ErrorInfo getErrorInfo() {
        return errorInfo;
    }
}
