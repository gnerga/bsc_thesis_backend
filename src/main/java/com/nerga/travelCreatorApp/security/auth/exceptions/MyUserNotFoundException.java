package com.nerga.travelCreatorApp.security.auth.exceptions;

public class MyUserNotFoundException extends UserException{

    public MyUserNotFoundException() {
    }

    public MyUserNotFoundException(String message) {
        super(message);
    }
}
