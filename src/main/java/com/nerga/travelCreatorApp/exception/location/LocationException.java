package com.nerga.travelCreatorApp.exception.location;

public abstract class LocationException extends RuntimeException{
    public LocationException() {
    }

    public LocationException(String message) {
        super(message);
    }
}
