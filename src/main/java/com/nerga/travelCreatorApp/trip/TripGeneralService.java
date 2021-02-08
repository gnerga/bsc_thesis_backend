package com.nerga.travelCreatorApp.trip;

import com.nerga.travelCreatorApp.common.response.Response;
import com.nerga.travelCreatorApp.location.LocationRepository;
import com.nerga.travelCreatorApp.security.auth.database.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class TripGeneralService {

    TripRepository tripRepository;
    LocationRepository locationRepository;
    UserRepository userRepository;
    ModelMapper modelMapper;

    @Autowired
    public TripGeneralService(TripRepository tripRepository, LocationRepository locationRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.tripRepository = tripRepository;
        this.locationRepository = locationRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public Response findAllTripsOrganizedByUserId(){
        return null;
    }
    public Response findAllTripsParticipatedByUserId(){
        return null;
    }

    public Response getAllTripOrganizers(){return  null;}
    public Response getAllTripParticipants(){return null;}

    public Response getOrganizedTripById(){return null;}
    public Response getParticipatedTripById(){return null;}

}
