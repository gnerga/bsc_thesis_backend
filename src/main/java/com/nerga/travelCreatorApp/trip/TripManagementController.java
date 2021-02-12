package com.nerga.travelCreatorApp.trip;

import com.nerga.travelCreatorApp.trip.dto.TripCreateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trip/management")
public class TripManagementController {


    private final TripManagementService tripManagementService;

    @Autowired
    public TripManagementController(TripManagementService tripManagementService) {
        this.tripManagementService = tripManagementService;
    }

    @PostMapping
    public ResponseEntity createTrip(@RequestBody TripCreateDto tripCreateDto){
        return tripManagementService.addTrip(tripCreateDto).toResponseEntity();
    }

    @PostMapping("/addOrganizer={tripId}&{userId}")
    public ResponseEntity addNewOrganizer(@PathVariable("tripId") Long tripId, @PathVariable("userId") Long userId){
        return tripManagementService.addNewOrganizerById(tripId, userId).toResponseEntity();
    }

    @PostMapping("/addParticipants={tripId}&{userId}")
    public ResponseEntity addNewParticipants(@PathVariable("tripId") Long tripId, @PathVariable("userId") Long userId){
        return tripManagementService.addNewParticipantById(tripId, userId).toResponseEntity();
    }

}
