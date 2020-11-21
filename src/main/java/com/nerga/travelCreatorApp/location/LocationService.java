package com.nerga.travelCreatorApp.location;

import com.nerga.travelCreatorApp.common.response.Response;
import com.nerga.travelCreatorApp.common.response.Success;
import com.nerga.travelCreatorApp.common.response.Error;
import com.nerga.travelCreatorApp.location.dto.LocationCreateDto;
import com.nerga.travelCreatorApp.location.dto.LocationDetailsDto;
import com.nerga.travelCreatorApp.location.Location;
import com.nerga.travelCreatorApp.location.exceptions.LocationNotFoundException;
import io.vavr.control.Option;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("locationService")
public class LocationService {

    private final LocationRepository locationRepository;
    private ModelMapper modelMapper;

    @Autowired
    public LocationService(LocationRepository locationRepository, ModelMapper modelMapper) {
        this.locationRepository = locationRepository;
        this.modelMapper = modelMapper;
    }

    public Response createNewLocation (LocationCreateDto locationCreateDto) {
        Location location = locationRepository.save(modelMapper.map(locationCreateDto, Location.class));
        return location!=null ? Success.accepted(location) : Error.badRequest("LOCATION_CANNOT_BE_CREATED");
    }

    public Response findAllLocations(){
        List<Location> locationList = locationRepository.findAll();
        return !locationList.isEmpty() ? Success.ok(locationList) : Error.badRequest("LOCATIONS_NOT_FOUND");
    }

    public Response findById(Long id){
        return null;
    }

    public Response findAllLocationsWithLocationName(String locationName){
        return null;
    }

    public Response findAllWithDescription(String fragmentOfTheDescription){
        return null;
    }

    public Response updateLocationById(Long id, LocationDetailsDto locationDetailsDto) {
        return null;
    }

    public Response removeLocationById(Long id){
        return null;
    }


}