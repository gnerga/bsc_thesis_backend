package com.nerga.travelCreatorApp.service;

import com.nerga.travelCreatorApp.dto.location.LocationDto;
import com.nerga.travelCreatorApp.model.Location;
import com.nerga.travelCreatorApp.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("locationService")
public class LocationService {

    private LocationRepository locationRepository;

    @Autowired
    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Location createNewLocation(LocationDto locationDetails){
        return new Location();
    }

}
