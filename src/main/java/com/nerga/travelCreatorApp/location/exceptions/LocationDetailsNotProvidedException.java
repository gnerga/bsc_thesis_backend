package com.nerga.travelCreatorApp.location.exceptions;

public class LocationDetailsNotProvidedException extends LocationException {

    public LocationDetailsNotProvidedException(){
    }

    public LocationDetailsNotProvidedException(String message) {
        super(message);
    }
}
