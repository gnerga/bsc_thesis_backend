package com.nerga.travelCreatorApp.service;

import com.nerga.travelCreatorApp.dto.location.LocationDto;
import com.nerga.travelCreatorApp.dto.location.LocationMapper;
import com.nerga.travelCreatorApp.exception.location.LocationNotFoundException;
import com.nerga.travelCreatorApp.model.Location;
import com.nerga.travelCreatorApp.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("locationService")
public class LocationService {

    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    @Autowired
    public LocationService(LocationRepository locationRepository, LocationMapper locationMapper) {
        this.locationRepository = locationRepository;
        this.locationMapper = locationMapper;
    }

    public Location createNewLocation(LocationDto locationDetails) {
        return locationRepository.save(locationMapper.transform(locationDetails));
    }

    public List<Location> findAllLocations() {
        return Optional.of(locationRepository.findAll())
                .orElseThrow(LocationNotFoundException::new);
    }

    public Location findById(Long id) {
        return Optional.of(locationRepository.findById(id)).get()
                .orElseThrow(LocationNotFoundException::new);
    }

    public List<Location> findByDescription(String elementOfDescription) {
        return Optional.of(locationRepository.findLocationsByLocationDescriptionContains(elementOfDescription))
                .orElseThrow(LocationNotFoundException::new);
    }

    public Location findByName(String locationName) {
        return Optional.of(locationRepository.findLocationByLocationName(locationName))
                .orElseThrow(LocationNotFoundException::new);
    }

}