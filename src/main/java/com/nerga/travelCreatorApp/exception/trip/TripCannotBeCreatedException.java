package com.nerga.travelCreatorApp.exception.trip;

public class TripCannotBeCreatedException extends TripException {

    public TripCannotBeCreatedException() {
    }

    public TripCannotBeCreatedException(String message) {
        super(message);
    }
}
