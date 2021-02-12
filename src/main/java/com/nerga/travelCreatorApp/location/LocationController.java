package com.nerga.travelCreatorApp.location;

import com.nerga.travelCreatorApp.location.dto.LocationCreateDto;
import com.nerga.travelCreatorApp.location.dto.LocationDetailsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/location")
public class LocationController {

    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping(path="createLocation")
    @ResponseBody
    public ResponseEntity createNewLocation(@RequestBody LocationCreateDto locationDetails) {
        return locationService.createNewLocation(locationDetails).toResponseEntity();
    }

    @GetMapping(path="findAll")
    @ResponseBody
    public ResponseEntity findAll(){
        return locationService.findAllLocations().toResponseEntity();
    }

    @GetMapping(path="findById/{id}")
    public ResponseEntity findLocationById(@PathVariable("id") Long id) {
        return locationService.findById(id).toResponseEntity();
    }

    @GetMapping(path="findByDescription/{desc}")
    public ResponseEntity findLocationByDescription(@PathVariable("desc") String elementOfDescription) {
        return locationService.findAllWithDescription(elementOfDescription).toResponseEntity();
    }

    @GetMapping(path="findByName/{name}")
    public ResponseEntity findLocationByName(@PathVariable("name") String name) {
        return locationService.findAllLocationsWithLocationName(name).toResponseEntity();
    }

    @PutMapping(path="updateById/{id}")
    public ResponseEntity updateById(@PathVariable("id") Long id, LocationDetailsDto locationDetailsDto) {
        return locationService.updateLocationById(id, locationDetailsDto).toResponseEntity();
    }

    @DeleteMapping(path="deleteById/{id}")
    public ResponseEntity deleteById(@PathVariable("id")Long id){
        return locationService.deleteLocationById(id).toResponseEntity();
    }


}
