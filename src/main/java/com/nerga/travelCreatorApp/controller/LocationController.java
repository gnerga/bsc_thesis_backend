package com.nerga.travelCreatorApp.controller;

import com.nerga.travelCreatorApp.model.Location;
import com.nerga.travelCreatorApp.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController("/location")
public class LocationController {

    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    public Location createNewLocation() {
        return null;
    }

}
