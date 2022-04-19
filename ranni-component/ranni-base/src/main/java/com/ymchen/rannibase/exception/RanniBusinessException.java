package com.ymchen.rannibase.exception;

public class RanniBusinessException extends RuntimeException {

    private static final long serialVersionUID = -3758247322278303440L;

    public RanniBusinessException() {
    }

    public RanniBusinessException(final String message) {
        super(message);
    }


    public RanniBusinessException(final String message, final Throwable cause) {
        super(message, cause);
    }


    public RanniBusinessException(final Throwable cause) {
        super(cause);
    }
}
