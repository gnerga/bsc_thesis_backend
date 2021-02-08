package com.nerga.travelCreatorApp.trip;

import com.nerga.travelCreatorApp.common.response.Response;
import com.nerga.travelCreatorApp.location.LocationRepository;
import com.nerga.travelCreatorApp.security.auth.database.UserEntity;
import com.nerga.travelCreatorApp.security.auth.database.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TripUserService {

    TripRepository tripRepository;
    LocationRepository locationRepository;
    UserRepository userRepository;
    ModelMapper modelMapper;

    @Autowired
    public TripUserService(TripRepository tripRepository, LocationRepository locationRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.tripRepository = tripRepository;
        this.locationRepository = locationRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public Response addUserToExpenseById(){return null;}
    public Response addUsersListToExpenseById(){return null;}
    public Response createExpense(){return null;}
    public Response updateAmount(){return null;}
    public Response updateExpenseById(){return null;}
    public Response addNewDateProposition(){
        return null;
    }
    public Response leaveTripWithGivenId(){ return null; }
    public Response addPost(){return null;}
    public Response handUp(){return null;}
    public Response handDown(){return null;}



}
