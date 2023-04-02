package com.cheng.common.error.system;

import com.cheng.common.error.api.IProjectModule;

/**
 * @time: 2022/11/16 18:00
 * @author: licheng
 * @desc: 系统模块
 */
public class SystemIProjectModule implements IProjectModule {

    public static final SystemIProjectModule INSTANCE = new SystemIProjectModule();

    @Override
    public int getProjectCode() {
        return 0;
    }

    @Override
    public int getModuleCode() {
        return 0;
    }

    @Override
    public String getProjectName() {
        return "default";
    }

    @Override
    public String getModuleName() {
        return "default";
    }
}
