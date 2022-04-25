package com.ymchen.rannibase.controller;

import com.ymchen.rannibase.entity.base.RanniResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalController {

    @ExceptionHandler(value = Exception.class)
    public RanniResult exceptionHandler(Exception exception) {
        log.error("统一异常处理，错误:{}", exception.getMessage());
        return RanniResult.ERROR("统一异常：服务器内部错误" + exception.getMessage());
    }
}
