package com.cheng.framework.springmvc;

import com.cheng.common.error.system.SystemErrorCodes;
import com.cheng.common.response.CustomResult;
import com.cheng.common.response.Result;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


@Order(0)
@RestControllerAdvice
public class ResultValueWrapper implements ResponseBodyAdvice<Object> {
//    private final Logger logger = LoggerFactory.getLogger(ResultValueWrapper.class);


    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 判断如果converterType 是否为HttpMessageConverter的子类或者实现类，是返回true
        return HttpMessageConverter.class.isAssignableFrom(converterType);
    }


    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
//        logger.info("result value wrapper : 结果包装----> beforeBodyWrite" + body);
        if (body instanceof Result<?>) {
            return body;
        }

        if (body instanceof CustomResult<?>) {
            return ((CustomResult<?>) body).getData();
        }

        String rootPath = ((ServletServerHttpRequest) request).getServletRequest().getServletPath();
        if (StringUtils.equals(rootPath, "/error")) {
            return Result.error(SystemErrorCodes.SYSTEM_ERROR.getCode(), body.toString());
        }else if(rootPath.contains("swagger")){
            //TODO 此处返回Result时 springdoc 无法解析返回结果； 最终导致接口文档不显示
              return body;
        }  else {
            return Result.success(body);
        }
    }
}
