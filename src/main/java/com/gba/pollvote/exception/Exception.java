package com.gba.pollvote.exception;


import lombok.Getter;


@Getter
public class Exception extends RuntimeException {

    private final String[] args;

    public Exception(String message, String... args) {
        super(message);
        this.args = args;
    }

    public Exception(String message, Throwable cause, String... args) {
        super(message, cause);
        this.args = args;
    }
}