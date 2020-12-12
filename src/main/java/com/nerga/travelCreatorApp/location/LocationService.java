package com.nerga.travelCreatorApp.location;

import com.nerga.travelCreatorApp.common.response.Response;
import com.nerga.travelCreatorApp.common.response.Success;
import com.nerga.travelCreatorApp.common.response.Error;
import com.nerga.travelCreatorApp.location.dto.LocationCreateDto;
import com.nerga.travelCreatorApp.location.dto.LocationDetailsDto;
import io.vavr.control.Option;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
        return Option.of(locationRepository.findById(id))
                .map(locationEntity -> modelMapper.map(locationEntity, LocationDetailsDto.class))
                .toEither(Error.badRequest("LOCATION_WITH_GIVEN_ID_NOT_FOUND"))
                .fold(Function.identity(), Success::ok);
    }

    public Response findAllLocationsWithLocationName(String locationName){
        List<Location> locationsList = locationRepository.findLocationsByLocationNameContains(locationName);

        return !locationsList.isEmpty() ? Success.ok(returnLocationDtosList(locationsList))
                : Error.badRequest("LOCATIONS_WITH_GIVEN_LOCATION_NAME_NOT_FOUND");

    }

    public Response findAllWithDescription(String fragmentOfTheDescription){

        List<Location> locationsList = findAllLocationWithDescriptionContains(fragmentOfTheDescription);
        return !locationsList.isEmpty() ? Success.ok(returnLocationDtosList(locationsList))
                : Error.badRequest("LOCATION_WITH_GIVEN_FRAGMENT_NOT_FOUND");
    }

    public Response updateLocationById(Long id, LocationDetailsDto locationDetailsDto) {
        return Option.ofOptional(locationRepository.findById(id))
                .peek(location -> locationRepository.save(location.updateLocationEntity(locationDetailsDto)))
                .toEither(Error.badRequest("LOCATION_WITH_GIVEN_ID_CANNOT_BE_FOUND"))
                .fold(Function.identity(), Success::ok);

    }

    public Response deleteUserById(Long id){
        return Option.ofOptional(locationRepository.findById(id))
                .peek(locationRepository::delete)
                .toEither(Error.badRequest("LOCATION_NOT_FOUND"))
                .fold(Function.identity(), Success::ok);
    }

    private List<Location> findAllLocationWithDescriptionContains(String fragmentOfDescription){
        List<Location> listOfAllLocation = locationRepository.findAll();
        return listOfAllLocation
                .stream()
                .filter( (location) -> location.getLocationDescription().toLowerCase(Locale.ROOT)
                        .contains(fragmentOfDescription.toLowerCase(Locale.ROOT)))
                .collect(Collectors.toList());
    }

    private List<LocationDetailsDto> returnLocationDtosList(List<Location> locationList){
        return locationList
                .stream()
                .map(location -> modelMapper
                        .map(location, LocationDetailsDto.class)
                ).collect(Collectors.toList());
    }


}