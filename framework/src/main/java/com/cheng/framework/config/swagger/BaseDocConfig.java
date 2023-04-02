package com.cheng.framework.config.swagger;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.apache.commons.lang3.StringUtils;

/**
 * @author: cheng
 * @date: 2022/11/23 13:36
 * @desc: 接口文档默认配置
 */
public abstract class BaseDocConfig {


    protected abstract String title();

    protected abstract String desc();

    protected abstract String version();

    public String websiteUrl() {
        return "";
    }

    public String websiteDesc() {
        return "";
    }

    public OpenAPI buildAPIDoc() {
        String title = StringUtils.defaultString(title());
        String desc = StringUtils.defaultString(desc());
        String version = StringUtils.defaultString(version(), "1.0.0");
        return new OpenAPI()
                .info(new Info().title(title)
                                .description(desc)
                                .version(version)
                        //外部文档地址及描述
                ).externalDocs(new ExternalDocumentation()
                        .description(websiteDesc())
                        .url(websiteUrl())
                );
    }

    protected abstract OpenAPI build();

}
