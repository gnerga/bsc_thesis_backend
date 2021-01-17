package com.nerga.travelCreatorApp.location;

import com.nerga.travelCreatorApp.common.propertymap.ApplicationPropertyMaps;
import com.nerga.travelCreatorApp.common.response.Error;
import com.nerga.travelCreatorApp.common.response.Response;
import com.nerga.travelCreatorApp.common.response.Success;
import com.nerga.travelCreatorApp.location.dto.LocationCreateDto;
import com.nerga.travelCreatorApp.location.dto.LocationDetailsDto;
import com.nerga.travelCreatorApp.security.auth.database.UserEntity;
import com.nerga.travelCreatorApp.security.auth.database.UserRepository;
import com.nerga.travelCreatorApp.security.auth.exceptions.MyUserNotFoundException;
import com.nerga.travelCreatorApp.security.auth.exceptions.UserException;
import io.vavr.control.Option;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service("locationService")

public class LocationService {

    private final LocationRepository locationRepository;
    private final UserRepository userRepository;
    private ModelMapper modelMapper;

    @Autowired
    public LocationService(LocationRepository locationRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.locationRepository = locationRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.modelMapper.addMappings(ApplicationPropertyMaps.userEntityFieldMapping());

    }

    public Response createNewLocation (LocationCreateDto locationCreateDto) {
        UserEntity owner;

        try{
            owner = Option.ofOptional(userRepository.findById(locationCreateDto.getOwner().getId()))
                    .getOrElseThrow(()-> new MyUserNotFoundException("USER_NOT_FOUND"));
        } catch (UserException e) {
            return Error.badRequest("USER_NOT_FOUND");
        }

        Location location = modelMapper.map(locationCreateDto, Location.class);
        location.setOwner(owner);

        location = locationRepository.save(location);

        LocationDetailsDto locationDetailsDto = modelMapper.map(location, LocationDetailsDto.class);

        return Success.accepted(locationDetailsDto);
    }

    public Response updateLocationById(Long id, LocationDetailsDto locationDetailsDto) {
        return Option.ofOptional(locationRepository.findById(id))
                .map(location -> locationRepository.save(location.updateLocationEntity(locationDetailsDto)))
                .map(locationUpdated -> modelMapper.map(locationUpdated, LocationDetailsDto.class))
                .toEither(Error.badRequest("LOCATION_WITH_GIVEN_ID_CANNOT_BE_FOUND"))
                .fold(Function.identity(), Success::ok);
    }

    public Response findAllLocations(){
        List<Location> locationList = locationRepository.findAll();
        List<LocationDetailsDto> locationDetailsDtoList = returnLocationDTOSList(locationList);
        return !locationList.isEmpty() ? Success.ok(locationDetailsDtoList) : Error.badRequest("LOCATIONS_NOT_FOUND");
    }

    public Response findById(Long id){
        return Option.ofOptional(locationRepository.findById(id))
                .map(locationEntity -> modelMapper.map(locationEntity, LocationDetailsDto.class))
                .toEither(Error.badRequest("LOCATION_WITH_GIVEN_ID_NOT_FOUND"))
                .fold(Function.identity(), Success::ok);
    }

    public Response findAllLocationsWithLocationName(String locationName){
        List<Location> locationsList = findAllLocationWithLocationNameContainsGivenTextFragment(locationName);

        return !locationsList.isEmpty() ? Success.ok(returnLocationDTOSList(locationsList))
                : Error.badRequest("LOCATIONS_WITH_GIVEN_LOCATION_NAME_NOT_FOUND");

    }

    public Response findAllWithDescription(String fragmentOfTheDescription){

        List<Location> locationsList = findAllLocationWithDescriptionContains(fragmentOfTheDescription);
        return !locationsList.isEmpty() ? Success.ok(returnLocationDTOSList(locationsList))
                : Error.badRequest("LOCATION_WITH_GIVEN_FRAGMENT_NOT_FOUND");
    }

    public Response deleteLocationById(Long id){
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

    private List<Location> findAllLocationWithLocationNameContainsGivenTextFragment(String fragmentOfName) {
        List<Location> listOfAllLocation = locationRepository.findAll();
        return listOfAllLocation.stream()
                .filter(location -> location.getLocationName().toLowerCase(Locale.ROOT)
                .contains(fragmentOfName.toLowerCase(Locale.ROOT)))
                .collect(Collectors.toList());
    }

    private List<LocationDetailsDto> returnLocationDTOSList(List<Location> locationList){
        return locationList
                .stream()
                .map(location -> modelMapper
                        .map(location, LocationDetailsDto.class)
                ).collect(Collectors.toList());
    }

}