package com.nerga.travelCreatorApp.service;


import com.nerga.travelCreatorApp.dto.trip.TripCreateDto;
import com.nerga.travelCreatorApp.dto.trip.TripOutputDto;
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

    public List<TripOutputDto> findAllTrips() {
        Optional<List<Trip>> optionalTripList = Optional.of(tripRepository.findAll());
        return optionalTripList.get()
                .stream()
                .map(trip -> new TripOutputDto(
                    trip.getTripId(),
                    trip.getTripName(),
                    trip.getTripDescription(),
                    trip.getLocation().getLocationName(),
                    trip.getLocation().getLocationDescription(),
                    trip.getLocation().getGoogleMapUrl(),
                        TripOutputDto.test()
                )).collect(Collectors.toList());
    }

    public TripOutputDto findTripById(Long id){
        Optional<Trip> optionalTrip = Optional.of(tripRepository.findById(id)).orElseThrow(TripNotFoundException::new);
        if (optionalTrip.isEmpty()){
            throw new TripNotFoundException();
        }
        return new TripOutputDto(
                optionalTrip.get().getTripId(),
                optionalTrip.get().getTripName(),
                optionalTrip.get().getTripDescription(),
                optionalTrip.get().getLocation().getLocationName(),
                optionalTrip.get().getLocation().getLocationDescription(),
                optionalTrip.get().getLocation().getGoogleMapUrl(),
                TripOutputDto.test()
        );
    }


}
