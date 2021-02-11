package com.nerga.travelCreatorApp.trip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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



}
