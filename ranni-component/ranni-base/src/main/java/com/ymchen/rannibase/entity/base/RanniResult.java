package com.ymchen.rannibase.entity.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;


@Data
@AllArgsConstructor
public class RanniResult implements Serializable {
    private static final long serialVersionUID = 8589326587721432681L;

    private int code;

    private String message;

    private Object object;

    public static RanniResult SUCCESS() {
        return new RanniResult(HttpStatus.OK.value(), null, null);
    }

    public static RanniResult SUCCESS(Object object) {
        return new RanniResult(HttpStatus.OK.value(), null, object);
    }

    public static RanniResult SUCCESS(String message, Object object) {
        return new RanniResult(HttpStatus.OK.value(), message, object);
    }

    public static RanniResult ERROR() {
        return new RanniResult(HttpStatus.OK.value(), null, null);
    }

    public static RanniResult ERROR(Object object) {
        return new RanniResult(HttpStatus.OK.value(), null, object);
    }

    public static RanniResult ERROR(String message) {
        return new RanniResult(HttpStatus.OK.value(), message, null);
    }

    public static RanniResult ERROR(String message, Object object) {
        return new RanniResult(HttpStatus.OK.value(), message, object);
    }

}
