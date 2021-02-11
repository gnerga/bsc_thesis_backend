package com.nerga.travelCreatorApp.trip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trip/user")

public class TripUserController {

    private final TripUserService tripUserService;

    @Autowired
    public TripUserController(TripUserService tripUserService) {
        this.tripUserService = tripUserService;
    }

   @PostMapping
    public String test(){
        return "Hello world";
   }

}