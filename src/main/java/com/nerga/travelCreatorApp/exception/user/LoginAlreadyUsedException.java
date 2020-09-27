package com.nerga.travelCreatorApp.exception.user;

public class LoginAlreadyUsedException extends UserException{

    public LoginAlreadyUsedException() {
    }

    public LoginAlreadyUsedException(String message) {
        super(message);
    }
}
