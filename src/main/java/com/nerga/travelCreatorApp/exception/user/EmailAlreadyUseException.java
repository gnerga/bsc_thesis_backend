package com.nerga.travelCreatorApp.exception.user;

public class EmailAlreadyUseException extends UserException {

    public EmailAlreadyUseException() {
    }

    public EmailAlreadyUseException(String message) {
        super(message);
    }
}
