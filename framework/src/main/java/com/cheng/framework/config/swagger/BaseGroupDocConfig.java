package com.cheng.framework.config.swagger;

import org.springdoc.core.models.GroupedOpenApi;

/**
 * @time: 2022/12/12 18:44
 * @author: licheng
 * @desc: 接口文档分组基础配置类
 */
public abstract class BaseGroupDocConfig {

    protected abstract String groupName();


    protected abstract String[] groupPaths();

    public GroupedOpenApi createGroup() {
        return GroupedOpenApi.builder()
                .group(groupName())
                .displayName(displayName())
                .pathsToExclude(excludePaths())
                .pathsToMatch(groupPaths())
                .build();
    }

    protected abstract GroupedOpenApi build();


    public String displayName() {
        return this.groupName();
    }

    public String[] excludePaths() {
        return new String[]{};
    }


}
