package com.nerga.travelCreatorApp.service;


import com.nerga.travelCreatorApp.dto.trip.TripCreateDto;
import com.nerga.travelCreatorApp.exception.location.LocationNotFoundException;
import com.nerga.travelCreatorApp.exception.trip.TripCannotBeCreatedException;
import com.nerga.travelCreatorApp.exception.trip.TripNotFoundException;
import com.nerga.travelCreatorApp.model.Location;
import com.nerga.travelCreatorApp.model.Trip;
import com.nerga.travelCreatorApp.repository.LocationRepository;
import com.nerga.travelCreatorApp.repository.TripRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("tripService")
public class TripService {

    private final TripRepository tripRepository;
    private final LocationRepository locationRepository;

    @Autowired
    public TripService(TripRepository tripRepository, LocationRepository locationRepository) {
        this.tripRepository = tripRepository;
        this.locationRepository = locationRepository;
    }

    public Trip addTrip (TripCreateDto tripCreateDto) {

        Optional<Location> optionalLocation = Optional.of(
                locationRepository
                        .findById(tripCreateDto.getLocationId()))
                        .orElseThrow(LocationNotFoundException::new);

        if (optionalLocation.isEmpty()){
            throw new TripCannotBeCreatedException("Location not found");
        }

        Location location = optionalLocation.get();

        Trip trip = new Trip();
        trip.setTripName(tripCreateDto.getTripName());
        trip.setTripDescription(tripCreateDto.getTripDescription());
        trip.setLocation(location);

        trip = tripRepository.save(trip);

        return trip;
    }

//    public List<Trip> findAllTrips () {
//
//        Optional<List<Trip>> optionalTripList = Optional.of(tripRepository.findAll());
//        return optionalTripList.get();
//    }

    public List<JSONObject> findAllTrips() {
        Optional<List<Trip>> optionalTripList = Optional.of(tripRepository.findAll());
        List<JSONObject> objectList = optionalTripList.get().stream().map(this::parseTripToJsonObject).collect(Collectors.toList());
        return objectList;
    }

    private JSONObject parseTripToJsonObject(Trip trip){

        JSONObject location = new JSONObject();
//        location.put("locationId", trip.getLocation().getLocationId());
        location.put("locationName", trip.getLocation().getLocationName());
        location.put("locationDescription", trip.getLocation().getLocationDescription());
        location.put("googleMapUrl", trip.getLocation().getGoogleMapUrl());

        JSONObject tripJson = new JSONObject();
        tripJson.put("tripId", trip.getTripId());
        tripJson.put("tripName", trip.getTripName());
        tripJson.put("tripDescription", trip.getTripDescription());
        tripJson.put("location", location);
        return tripJson;
    }

}
