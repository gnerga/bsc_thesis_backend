package com.nerga.travelCreatorApp.location.exceptions;

public abstract class LocationException extends RuntimeException{
    public LocationException() {
    }

    public LocationException(String message) {
        super(message);
    }
}
