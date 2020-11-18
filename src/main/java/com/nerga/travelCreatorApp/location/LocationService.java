package com.nerga.travelCreatorApp.location;

import com.nerga.travelCreatorApp.location.exceptions.LocationNotFoundException;
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

    public Location createNewLocation(LocationCreateDto locationDetails) {
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