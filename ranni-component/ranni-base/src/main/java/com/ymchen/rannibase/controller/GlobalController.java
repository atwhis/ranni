package com.ymchen.rannibase.controller;

import com.ymchen.rannibase.entity.base.RanniResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@Slf4j
@RestControllerAdvice(basePackages = {"com.ymchen"})
public class GlobalController implements ResponseBodyAdvice {

    @ExceptionHandler(value = Exception.class)
    public RanniResult exceptionHandler(Exception exception) {
        log.error("统一异常处理，错误:{}", exception.getMessage());
        return RanniResult.ERROR("统一异常：服务器内部错误" + exception.getMessage());
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object result, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {

        if (result instanceof RanniResult || result instanceof String) {
            return result;
        }

        return RanniResult.SUCCESS(result);
    }
}
