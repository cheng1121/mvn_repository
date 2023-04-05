package com.cheng.framework.springmvc;

import com.cheng.common.error.exception.IErrorCodeException;
import com.cheng.common.error.manager.ErrorInfo;
import com.cheng.common.error.system.HttpCodes;
import com.cheng.common.error.system.SystemErrorCodes;
import com.cheng.common.response.Result;
import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ErrorExceptionHandle {

    public static final Joiner.MapJoiner JOINER = Joiner.on(",").withKeyValueSeparator(": ");
    private final Logger logger = LoggerFactory.getLogger(ErrorExceptionHandle.class);

    private static final String ERR_MSG_TEMPLATE = "error, request params: \n\r {} \n\r error message: \n\r";

    private static final String ERR_MSG_BAD_REQUEST = "BadRequestException, request:\r\n {} \r\n";
    private static final String ERR_MSG_TIPS = "tips: \r\n request: {} \r\n error message: {}";

    /**
     * 错误响应
     *
     * @param request
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Throwable.class)
    public ResponseEntity<Result<?>> processException(HttpServletRequest request, Exception e) {
        Pair<Throwable, String> pair = getExceptionMessage(e);
        if (e instanceof IErrorCodeException) {
            if (e.getCause() != null) {
                logger.error(ERR_MSG_TIPS, parseParam(request), pair.getRight());
            } else {
                logger.error(ERR_MSG_TIPS, parseParam(request), pair.getRight());
            }
            ErrorInfo errorInfo = ((IErrorCodeException) e).getErrorInfo();
            Result<?> apiResult;
            if (errorInfo == null) {
                apiResult = Result.error(SystemErrorCodes.SYSTEM_ERROR.getCode(), pair.getRight());
            } else {
                apiResult = Result.error(errorInfo.getCode(), errorInfo.getMsg());
            }

            return new ResponseEntity<>(apiResult, HttpStatus.OK);
        }

        logger.error(ERR_MSG_TEMPLATE, parseParam(request), pair.getLeft());
        Result<String> errorResult = Result.error(SystemErrorCodes.SYSTEM_ERROR.getCode(), pair.getLeft().getClass().getSimpleName() + ": " + pair.getRight());
        return new ResponseEntity<>(errorResult, HttpStatus.OK);
    }


    /**
     * 请求参数异常
     *
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Result<String>> badRequestException(HttpServletRequest request, MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder builder = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            builder.append(fieldError.getField())
                    .append(fieldError.getDefaultMessage())
                    .append(", ");
        }
        logger.error(ERR_MSG_BAD_REQUEST, parseParam(request), e);
        return new ResponseEntity<>(Result.error(HttpCodes.BAD_REQUEST.getStatus(), builder.toString()), HttpStatus.OK);
    }


    /**
     * 解析请求参数
     *
     * @param request
     * @return
     */
    public String parseParam(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        HashMap<String, String> map = Maps.newHashMapWithExpectedSize(parameterMap.size());
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            map.put(entry.getKey(), ArrayUtils.isNotEmpty(entry.getValue()) ? entry.getValue()[0] : "");
        }
        return JOINER.join(map);
    }


    public Pair<Throwable, String> getExceptionMessage(Throwable e) {
        Throwable detail = e;
        while (detail.getCause() != null) {
            detail = detail.getCause();
        }

        return ImmutablePair.of(detail, detail.getMessage());
    }

}
