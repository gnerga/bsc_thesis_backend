package com.nerga.travelCreatorApp.security.auth.exceptions;

public class EmailAlreadyUseException extends UserException {

    public EmailAlreadyUseException() {
    }

    public EmailAlreadyUseException(String message) {
        super(message);
    }
}
