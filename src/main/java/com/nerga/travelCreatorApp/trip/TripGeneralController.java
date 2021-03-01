package com.nerga.travelCreatorApp.trip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trip/general")
public class TripGeneralController {

    private final TripGeneralService tripGeneralService;

    @Autowired
    public TripGeneralController(TripGeneralService tripGeneralService) {
        this.tripGeneralService = tripGeneralService;
    }

    @GetMapping("/organized")
    public ResponseEntity findAllOrganizedTrip(){
        return tripGeneralService.findAllTripsOrganizedByLoggedUser().toResponseEntity();
    }

    @GetMapping("/participated")
    public ResponseEntity findAllParticipatedTrip(){
        return tripGeneralService.findAllTripsParticipatedByLoggedUser().toResponseEntity();
    }

    @GetMapping("/tripOrganizers={tripId}")
    public ResponseEntity findAllTripOrganizers(@PathVariable("tripId") Long tripId){
        return tripGeneralService.getAllTripOrganizers(tripId).toResponseEntity();
    }

    @GetMapping("/tripParticipants={tripId}")
    public ResponseEntity finalAllTripParticipants(@PathVariable("tripId") Long tripId){
        return tripGeneralService.getAllTripParticipants(tripId).toResponseEntity();
    }

    @GetMapping("/tripUsers={tripId}")
    public ResponseEntity findAllTripOrganizersAndParticipants(@PathVariable("tripId") Long tripId){
        return tripGeneralService.getAllTripParticipantsAndOrganizers(tripId).toResponseEntity();
    }

    @GetMapping("/tripUsersNotIncludedToExpense={tripId}&{expenseId}")
    public ResponseEntity findAllParticipantsNotIncludedToExpenseWithGivenId(@PathVariable("tripId") Long tripId, @PathVariable("expenseId") Long expenseId){
        return tripGeneralService.getAllParticipantsNotIncludedToExpenseWithGivenId(tripId, expenseId).toResponseEntity();
    }

    @GetMapping("/trip={tripId}")
    public ResponseEntity findTrip(@PathVariable("tripId") Long tripId){
        return tripGeneralService.getTripById(tripId).toResponseEntity();
    }

}
