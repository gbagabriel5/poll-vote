package com.gba.pollvote.exception;

import java.io.Serial;

public class InvalidCpfException extends GlobalException {

    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidCpfException(String message, String... args) {
        super(message, args);
    }

    public InvalidCpfException(String message, Throwable cause, String... args) {
        super(message, cause, args);
    }

}