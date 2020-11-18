package com.nerga.travelCreatorApp.trip.exceptions;

public class TripCannotBeCreatedException extends TripException {

    public TripCannotBeCreatedException() {
    }

    public TripCannotBeCreatedException(String message) {
        super(message);
    }
}
