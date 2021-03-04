package com.gba.pollvote.exception;

import java.io.Serial;

public final class NullPointerException extends GlobalException {

    @Serial
    private static final long serialVersionUID = 1L;

    public NullPointerException(String message, String... args) {
        super(message, args);
    }

    public NullPointerException(String message, Throwable cause, String... args) {
        super(message, cause, args);
    }
}
