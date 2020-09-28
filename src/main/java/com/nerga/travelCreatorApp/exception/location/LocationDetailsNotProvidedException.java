package com.nerga.travelCreatorApp.exception.location;

public class LocationDetailsNotProvidedException extends LocationException {

    public LocationDetailsNotProvidedException(){
    }

    public LocationDetailsNotProvidedException(String message) {
        super(message);
    }
}
