package com.nerga.travelCreatorApp.post.exception;

public class PostNotFoundException extends PostException{

    public PostNotFoundException() {
    }

    public PostNotFoundException(String message) {
        super(message);
    }
}
