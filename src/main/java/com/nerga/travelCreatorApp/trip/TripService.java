package com.nerga.travelCreatorApp.trip;

import com.nerga.travelCreatorApp.common.response.Response;
import com.nerga.travelCreatorApp.location.LocationRepository;
import com.nerga.travelCreatorApp.security.auth.database.UserEntity;
import com.nerga.travelCreatorApp.security.auth.database.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TripService {

    TripRepository tripRepository;
    LocationRepository locationRepository;
    UserRepository userRepository;
    ModelMapper modelMapper;

    @Autowired
    public TripService(TripRepository tripRepository, LocationRepository locationRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.tripRepository = tripRepository;
        this.locationRepository = locationRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public Response findAllOrganizedTripsByLoggedInUser(){
        return null;
    }

    public Response findAllParticipatedTripsByLoggedInUser(){
        return null;
    }

    public Response findAllOrganizedTripsByUserId(){
        return null;
    }

    public Response findAllParticipatedTripsByUserId(){
        return null;
    }

    public Response addNewDateProposition(){
        return null;
    }

    public Response removeDateProposition(){
        return null;
    }

    public Response findAllLoggedInUserDatePropositions(){
        return null;
    }

}
