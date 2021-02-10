package com.nerga.travelCreatorApp.trip;

import com.nerga.travelCreatorApp.common.response.Error;
import com.nerga.travelCreatorApp.common.response.Response;
import com.nerga.travelCreatorApp.datepropositionmatcher.DateProposition;
import com.nerga.travelCreatorApp.datepropositionmatcher.dto.DatePropositionDto;
import com.nerga.travelCreatorApp.location.LocationRepository;
import com.nerga.travelCreatorApp.security.auth.database.UserRepository;
import com.nerga.travelCreatorApp.security.auth.exceptions.MyUserNotFoundException;
import com.nerga.travelCreatorApp.security.auth.exceptions.UserException;
import io.vavr.control.Option;
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

    public Response createExpenseByTripId() {
        return null;
    }

    public Response addUserToExpenseByUserIdAndTripId() {
        return null;
    }

    public Response addUsersListToExpenseByUserIdAndTripIdAndExpenseId() {
        return null;
    }

    public Response updateAmountByTripAndExpenseIdAndUserId() {
        return null;
    }

    public Response updateExpenseById() {
        return null;
    }

    public Response addNewDateProposition(DatePropositionDto datePropositionDto, Long tripId) {
        Trip trip;
        try {
            trip = Option.ofOptional(tripRepository.findById(tripId))
                    .getOrElseThrow(() -> new MyUserNotFoundException("USER_NOT_FOUND"));
        } catch (UserException e) {
            return Error.notFound("USER_NOT_FOUND");
        }

        DateProposition proposition = modelMapper.map(datePropositionDto, DateProposition.class);
        System.out.println(trip.getDatePropositionMatcher().getDatePropositionList().size());
        trip.addDateProposition(proposition);
        System.out.println(trip.getDatePropositionMatcher().getDatePropositionList().size());
        trip.getDatePropositionMatcher().runAnalysis();




        return null;

    }

    public Response leaveTripWithGivenId() {
        return null;
    }

    public Response addPostByTripId() {
        return null;
    }

    public Response handUpByTripAndPostId() {
        return null;
    }

    public Response handDownByTripAndPostId() {
        return null;
    }

}
