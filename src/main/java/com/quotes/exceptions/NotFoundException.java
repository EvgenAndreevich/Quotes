package com.quotes.exceptions;

public class NotFoundException extends RuntimeException {
    public static final Integer code = 604;

    public NotFoundException(String message) {
        super(message);
    }
}
