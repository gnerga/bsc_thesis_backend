package com.nerga.travelCreatorApp.common.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Success<T> extends Response<T> {

    private final T content;

    public Success(HttpStatus status, T content) {
        super(status);
        this.content = content;
    }

    public static <T> Success<T> ok (T content) {
        return new Success<>(HttpStatus.OK, content);
    }


    public static <T> Success<T> accepted (T content){
        return new Success<>(HttpStatus.ACCEPTED, content);
    }

    public static <T> Success<T> created (T content){
        return new Success<>(HttpStatus.CREATED, content);
    }


    @Override
    public ResponseEntity<T> toResponseEntity() {
        return new ResponseEntity<>(content, status);
    }
}
