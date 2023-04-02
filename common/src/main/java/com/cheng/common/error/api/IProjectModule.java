package com.cheng.common.error.api;

import com.google.common.base.Preconditions;
import com.cheng.common.error.system.SystemIProjectModule;

/**
 * @time: 2022/11/16 17:52
 * @author: licheng
 * @desc: 项目或者模块需要实现的基础接口
 */
public interface IProjectModule {

    /**
     * 项目编码
     */
    int getProjectCode();

    /**
     * 模块编码
     */
    int getModuleCode();

    /**
     * 项目名称
     */
    String getProjectName();

    /**
     * 模块名称
     */
    String getModuleName();

    /**
     * 检查是否为同一个模块
     *
     * @param required
     * @param input
     */
    static void check(IProjectModule required, IProjectModule input) {
        Preconditions.checkNotNull(required);
        if (input != SystemIProjectModule.INSTANCE) {
            String str = required.getProjectName() + "-" + required.getModuleName()
                    + "(" + required.getProjectCode() + "-" + required.getModuleCode() + ")"
                    + " but input: " + input.getProjectName() + "-" + input.getModuleName()
                    + "(" + input.getProjectCode() + "-" + input.getModuleCode() + ")";
            Preconditions.checkState(required == input, "module not match, need: " + str);
        }
    }

}
