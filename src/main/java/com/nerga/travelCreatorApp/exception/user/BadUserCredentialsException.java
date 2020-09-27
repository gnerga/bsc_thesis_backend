package com.nerga.travelCreatorApp.exception.user;

public class BadUserCredentialsException extends UserException {

    public BadUserCredentialsException() {
    }

    public BadUserCredentialsException(String message) {
        super(message);
    }
}
