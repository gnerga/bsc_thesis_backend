package com.nerga.travelCreatorApp.location;

import com.nerga.travelCreatorApp.common.response.Response;
import com.nerga.travelCreatorApp.location.dto.LocationCreateDto;
import com.nerga.travelCreatorApp.location.dto.LocationDetailsDto;
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
        return null;
    }

//    public Response createNewLocation (LocationCreateDto locationCreateDto) {
//        return Option.ofOptional()
//                .map(locationRepository.save(convertToLocation(locationCreateDto)))
//    }

//    public Location createNewLocation(LocationCreateDto locationDetails) {
//        return locationRepository.save(convertToLocation(locationDetails));
//    }

//    public List<Location> findAllLocations() {
//        return Optional.of(locationRepository.findAll())
//                .orElseThrow(LocationNotFoundException::new);
//    }
//
//    public Location findById(Long id) {
//        return Optional.of(locationRepository.findById(id)).get()
//                .orElseThrow(LocationNotFoundException::new);
//    }
//
//    public List<Location> findByDescription(String elementOfDescription) {
//        return Optional.of(locationRepository.findLocationsByLocationDescriptionContains(elementOfDescription))
//                .orElseThrow(LocationNotFoundException::new);
//    }
//
//    public Location findByName(String locationName) {
//        return Optional.of(locationRepository.findLocationByLocationName(locationName))
//                .orElseThrow(LocationNotFoundException::new);
//    }

//    private LocationDetailsDto convertToDto(Location location){
//        return modelMapper.map(location, LocationDetailsDto.class);
//    }
//
//    private Location convertToLocation(LocationCreateDto locationCreateDto) {
//        return modelMapper.map(locationCreateDto, Location.class);
//    }

    private void

}