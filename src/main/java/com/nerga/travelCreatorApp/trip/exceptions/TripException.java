package com.nerga.travelCreatorApp.trip.exceptions;

public abstract class TripException extends RuntimeException{

    public TripException() {
    }

    public TripException(String message) {
        super(message);
    }
}
