package com.quotes.exceptions;

public class UsersAlreadyExistException extends RuntimeException {
    public static final Integer code = 606;

    public UsersAlreadyExistException(String message) {
        super(message);
    }
}
