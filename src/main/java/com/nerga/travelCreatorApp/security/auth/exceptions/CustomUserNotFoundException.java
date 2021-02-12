package com.nerga.travelCreatorApp.security.auth.exceptions;

public class CustomUserNotFoundException extends UserException{

    public CustomUserNotFoundException() {
    }

    public CustomUserNotFoundException(String message) {
        super(message);
    }
}
