package com.cheng.common.error.api;

import com.cheng.common.error.manager.ErrorManager;

/**
 * @time: 2022/11/16 14:28
 * @author: licheng
 * @desc:
 */
public interface IErrorCode {

    /**
     *  错误码
     */
    int getNodeNum();

    /**
     *  错误详细信息
     */
    String getMsg();


    /**
     * 获取完整的错误码
     */
    default int getCode(){
        return ErrorManager.generateCode(this);
    }

    default IProjectModule projectModule(){
        return ErrorManager.projectModule(this);
    }

}
