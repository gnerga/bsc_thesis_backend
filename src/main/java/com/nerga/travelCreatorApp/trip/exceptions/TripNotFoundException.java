package com.nerga.travelCreatorApp.trip.exceptions;

public class TripNotFoundException extends TripException{

    public TripNotFoundException() {
    }

    public TripNotFoundException(String message) {
        super(message);
    }
}
