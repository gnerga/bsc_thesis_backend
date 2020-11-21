package com.nerga.travelCreatorApp.location;

import com.nerga.travelCreatorApp.location.dto.LocationCreateDto;
import com.nerga.travelCreatorApp.location.exceptions.LocationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/location")
public class LocationController {

    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

//    @PostMapping(path="createLocation")
//    @ResponseBody
//    public Location createNewLocation(@RequestBody LocationCreateDto locationDetails) {
//        return locationService.createNewLocation(locationDetails);
//    }
//
//    @GetMapping(path="findAll")
//    @ResponseBody
//    public ResponseEntity findAll(){
//        try {
//            List<Location> locationsList = locationService.findAllLocations();
//            return ResponseEntity.ok(locationsList);
//
//        } catch (LocationException e) {
//            return new ResponseEntity(HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @GetMapping(path="findById/{id}")
//    public ResponseEntity findLocationById(@PathVariable("id") Long id) {
//        try{
//            Location location = locationService.findById(id);
//            return ResponseEntity.ok(location);
//        } catch (LocationException e) {
//            return new ResponseEntity(HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @GetMapping(path="findByDescription/{desc}")
//    public ResponseEntity findLocationByDescription(@PathVariable("desc") String elementOfDescription) {
//        try{
//            List<Location> location = locationService.findByDescription(elementOfDescription);
//            return ResponseEntity.ok(location);
//        } catch (LocationException e) {
//            return new ResponseEntity(HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @GetMapping(path="findByName/{name}")
//    public ResponseEntity findLocationByName(@PathVariable("name") String name) {
//        try{
//            Location location = locationService.findByName(name);
//            return ResponseEntity.ok(location);
//        } catch (LocationException e) {
//            return new ResponseEntity(HttpStatus.NOT_FOUND);
//        }
//    }


}
