package error.exception;

import com.cheng.common.error.api.IProjectModule;
import error.manager.ErrorInfo;

/**
 * @time: 2022/11/17 10:52
 * @author: licheng
 * @desc:
 */
public interface IErrorCodeException {

    /**
     * 错误信息
     * @return
     */
    ErrorInfo getErrorInfo();

    /**
     * 服务 + 模块信息
     * @return
     */
    IProjectModule projectModule();

}
