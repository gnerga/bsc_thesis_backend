package com.nerga.travelCreatorApp.trip;

import com.nerga.travelCreatorApp.trip.dto.TripCreateDto;
import com.nerga.travelCreatorApp.trip.dto.TripUpdateDto;
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

    @PutMapping("/addOrganizer={tripId}&{userId}")
    public ResponseEntity addNewOrganizer(@PathVariable("tripId") Long tripId, @PathVariable("userId") Long userId){
        return tripManagementService.addNewOrganizerById(tripId, userId).toResponseEntity();
    }

    @PutMapping("/addParticipant={tripId}&{userId}")
    public ResponseEntity addNewParticipants(@PathVariable("tripId") Long tripId, @PathVariable("userId") Long userId){
        return tripManagementService.addNewParticipantById(tripId, userId).toResponseEntity();
    }

    @PutMapping("/update={tripId}")
    public ResponseEntity updateTrip(@PathVariable("tripId") TripUpdateDto tripUpdateDto){
        return tripManagementService.updateTrip(tripUpdateDto).toResponseEntity();
    }

    @PutMapping("/removeParticipant={tripId}&{userId}")
    public ResponseEntity removeParticipants(@PathVariable("tripId")Long tripId, @PathVariable("userId")Long userId){
        return tripManagementService.removeParticipantById(tripId, userId).toResponseEntity();
    }

    @PutMapping("/removeOrganizer={tripId}&{userId}")
    public ResponseEntity removeOrganizers(@PathVariable("tripId")Long tripId, @PathVariable("userId")Long userId){
        return tripManagementService.removeOrganizerById(tripId, userId).toResponseEntity();
    }

}
