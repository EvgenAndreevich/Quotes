package com.quotes.exceptions;

public class NotHasAccessException extends RuntimeException {
    public static final Integer code = 605;

    public NotHasAccessException(String message) {
        super(message);
    }
}
