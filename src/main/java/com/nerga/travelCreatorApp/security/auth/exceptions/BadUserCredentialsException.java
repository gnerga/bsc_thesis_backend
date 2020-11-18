package com.nerga.travelCreatorApp.security.auth.exceptions;

public class BadUserCredentialsException extends UserException {

    public BadUserCredentialsException() {
    }

    public BadUserCredentialsException(String message) {
        super(message);
    }
}
