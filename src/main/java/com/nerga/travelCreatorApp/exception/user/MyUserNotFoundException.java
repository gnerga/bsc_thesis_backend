package com.nerga.travelCreatorApp.exception.user;

public class MyUserNotFoundException extends UserException{

    public MyUserNotFoundException() {
    }

    public MyUserNotFoundException(String message) {
        super(message);
    }
}
