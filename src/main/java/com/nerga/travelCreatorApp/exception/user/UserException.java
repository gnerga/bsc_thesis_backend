package com.nerga.travelCreatorApp.exception.user;

public abstract class UserException extends RuntimeException {

    public UserException() {}

    public UserException(String message) {
        super(message);
    }

}
