package com.nerga.travelCreatorApp.post.exception;

import com.nerga.travelCreatorApp.trip.exceptions.TripException;

public abstract class PostException extends RuntimeException {

    public PostException(){

    }

    public PostException(String message){super(message);}

}
