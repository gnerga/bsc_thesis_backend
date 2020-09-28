package com.nerga.travelCreatorApp.controller;

import com.nerga.travelCreatorApp.dto.location.LocationDto;
import com.nerga.travelCreatorApp.exception.location.LocationException;
import com.nerga.travelCreatorApp.model.Location;
import com.nerga.travelCreatorApp.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/location")
public class LocationController {

    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping(path="test")
    @ResponseBody
    public String helloUserMethod(){
        return "Hello !" ;
    }

    @PostMapping(path="create_location")
    @ResponseBody
    public Location createNewLocation(@RequestBody LocationDto locationDetails) {
        return locationService.createNewLocation(locationDetails);
    }

    @GetMapping(path="find_all")
    @ResponseBody
    public ResponseEntity findAll(){
        try {
            List<Location> locationsList = locationService.findAllLocations();
            return ResponseEntity.ok(locationsList);

        } catch (LocationException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path="findById/{id}")
    public ResponseEntity findLocationById(@PathVariable("id") Long id) {
        try{
            Location location = locationService.findById(id);
            return ResponseEntity.ok(location);
        } catch (LocationException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path="findByDescription/{desc}")
    public ResponseEntity findLocationByDescription(@PathVariable("desc") String elementOfDescription) {
        try{
            List<Location> location = locationService.findByDescription(elementOfDescription);
            return ResponseEntity.ok(location);
        } catch (LocationException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path="findByName/{name}")
    public ResponseEntity findLocationByName(@PathVariable("name") String name) {
        try{
            Location location = locationService.findByName(name);
            return ResponseEntity.ok(location);
        } catch (LocationException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }


}
