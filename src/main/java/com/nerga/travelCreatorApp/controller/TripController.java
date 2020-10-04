package com.nerga.travelCreatorApp.controller;

import com.nerga.travelCreatorApp.dto.trip.TripCreateDto;
import com.nerga.travelCreatorApp.dto.trip.TripOutputDto;
import com.nerga.travelCreatorApp.exception.trip.TripException;
import com.nerga.travelCreatorApp.service.TripService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trip")
public class TripController {
    private final TripService tripService;

    @Autowired
    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @PostMapping(path="test")
    @ResponseBody
    public String test(){return "Trip Test Done";}

    @PostMapping(path = "createTrip")
    @ResponseStatus
    public ResponseEntity createTrip(@RequestBody TripCreateDto tripCreateDto){
        try {
            tripService.addTrip(tripCreateDto);
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (TripException e) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }

    @GetMapping(path="findAll")
    public ResponseEntity findAll(){
        List<TripOutputDto> test = tripService.findAllTrips();
        return ResponseEntity.ok(test);
    }

    @GetMapping(path="findById/{id}")
    public ResponseEntity findById(@PathVariable("id") Long id){
        try{
            TripOutputDto test = tripService.findTripById(id);
            return ResponseEntity.ok(test);
        } catch (TripException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }





}
