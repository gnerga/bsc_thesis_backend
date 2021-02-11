package com.nerga.travelCreatorApp.trip;

import com.nerga.travelCreatorApp.trip.dto.TripCreateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trip")
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

}
