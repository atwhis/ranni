package com.ymchen.rannibase.entity.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;


@Data
@AllArgsConstructor
public class RanniResult<T> implements Serializable {
    private static final long serialVersionUID = 8589326587721432681L;

    private int code;

    private String message;

    private T data;

    public static <T> RanniResult<T> SUCCESS() {
        return new RanniResult(HttpStatus.OK.value(), null, null);
    }

    public static <T> RanniResult<T> SUCCESS(Object object) {
        return new RanniResult(HttpStatus.OK.value(), null, object);
    }

    public static <T> RanniResult<T> SUCCESS(String message, T object) {
        return new RanniResult(HttpStatus.OK.value(), message, object);
    }

    public static <T> RanniResult<T> SUCCESS(int code, String message, T object) {
        return new RanniResult(code, message, object);
    }

    public static <T> RanniResult<T> ERROR() {
        return new RanniResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), null, null);
    }

    public static <T> RanniResult<T> ERROR(Object object) {
        return new RanniResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), null, object);
    }

    public static <T> RanniResult<T> ERROR(String message) {
        return new RanniResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, null);
    }

    public static <T> RanniResult<T> ERROR(String message, T object) {
        return new RanniResult(HttpStatus.INTERNAL_SERVER_ERROR.value(), message, object);
    }

    public static <T> RanniResult<T> ERROR(int code, String message, T object) {
        return new RanniResult(code, message, object);
    }
}
