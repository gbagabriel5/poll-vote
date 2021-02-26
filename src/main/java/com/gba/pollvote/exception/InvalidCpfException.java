package com.gba.pollvote.exception;

import java.io.Serial;

public class InvalidCpfException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidCpfException(String message) {
        super(message);
    }

    public InvalidCpfException(String message, Throwable cause) {
        super(message, cause);
    }
}
