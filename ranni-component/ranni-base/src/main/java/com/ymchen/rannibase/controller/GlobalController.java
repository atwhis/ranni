package com.ymchen.rannibase.controller;

import com.ymchen.rannibase.entity.base.RanniResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice(basePackages = {"com.ymchen"})
public class GlobalController implements ResponseBodyAdvice {

    @ExceptionHandler(value = Exception.class)
    public RanniResult exceptionHandler(Exception exception) {
        log.error("统一异常处理，错误", exception);
        return RanniResult.ERROR("统一异常：服务器内部错误" + exception.getMessage());
    }

    @ExceptionHandler(value = BindException.class)
    public RanniResult exceptionHandler(BindException exception) {
        BindingResult exceptions = exception.getBindingResult();
        if (exceptions.hasErrors()) {
            String objectName = exceptions.getObjectName();
            List<ObjectError> errors = exceptions.getAllErrors();
            if (!errors.isEmpty()) {
                FieldError fieldError = (FieldError) errors.get(0);
                log.error("对象{},参数{},校验错误: {}", objectName, fieldError.getField(), fieldError.getDefaultMessage(), exception);
                String message = "字段" + fieldError.getField() + "," + fieldError.getDefaultMessage();
                return RanniResult.ERROR(HttpStatus.BAD_REQUEST.value(), message, null);
            }
        }
        return RanniResult.ERROR(HttpStatus.BAD_REQUEST.value(), "请求参数错误", null);
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
