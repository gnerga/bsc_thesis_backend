package com.nerga.travelCreatorApp.exception.trip;

public class TripNotFoundException extends TripException{

    public TripNotFoundException() {
    }

    public TripNotFoundException(String message) {
        super(message);
    }
}
