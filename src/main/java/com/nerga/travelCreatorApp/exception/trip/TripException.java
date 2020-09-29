package com.nerga.travelCreatorApp.exception.trip;

public abstract class TripException extends RuntimeException{

    public TripException() {
    }

    public TripException(String message) {
        super(message);
    }
}
