package com.nerga.travelCreatorApp.security.auth.exceptions;

public class LoginAlreadyUsedException extends UserException{

    public LoginAlreadyUsedException() {
    }

    public LoginAlreadyUsedException(String message) {
        super(message);
    }
}
