package com.nerga.travelCreatorApp.trip;


import com.nerga.travelCreatorApp.common.response.Error;
import com.nerga.travelCreatorApp.common.response.Response;
import com.nerga.travelCreatorApp.common.response.Success;
import com.nerga.travelCreatorApp.datepropositionmatcher.DateProposition;
import com.nerga.travelCreatorApp.location.Location;
import com.nerga.travelCreatorApp.security.auth.User;
import com.nerga.travelCreatorApp.security.auth.database.UserEntity;
import com.nerga.travelCreatorApp.security.auth.database.UserRepository;
import com.nerga.travelCreatorApp.trip.dto.TripCreateDto;
import com.nerga.travelCreatorApp.location.LocationRepository;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Option;
import io.vavr.control.Validation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

//@Service("tripService")
public class TripService {

    private final TripRepository tripRepository;
    private final LocationRepository locationRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TripService(TripRepository tripRepository,
                       LocationRepository locationRepository,
                       UserRepository userRepository,
                       ModelMapper modelMapper) {
        this.tripRepository = tripRepository;
        this.locationRepository = locationRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public Response addTrip(TripCreateDto tripCreateDto){
        return isUserAndLocationExists(tripCreateDto)
                .map(userEntityAndLocation -> createTrip(userEntityAndLocation, tripCreateDto))
                .map(tripRepository::save)
                .fold(Function.identity(), Success::ok);
    }

    public Response getAllTrips(){
        List<Trip> tripList = tripRepository.findAll();
        return !tripList.isEmpty() ? Success.ok(tripList) : Error.badRequest("TRIP_LIST_IS_EMPTY");
    }

//    public TripOutputDto findTripById(Long id){
//        Optional<Trip> trip = Optional.of(tripRepository.findById(id)).orElseThrow(TripNotFoundException::new);
//        if (trip.isEmpty()){
//            throw new TripNotFoundException();
//        }
//        return tripToTripDto(trip.get());
//    }
//
//    public List<TripOutputDto> findOrganizedTripsByUserId(Long userId){
//        User user = findUserById(userId);
//        List<Trip> trips = findOrganizedTripsByUser(user);
//        return trips
//                .stream()
//                .map(this::tripToTripDto)
//                .collect(Collectors.toList());
//    }
//
//    public List<TripOutputDto> findParticipatedTripsByUserId(Long userId){
//        User user = findUserById(userId);
//        List<Trip> trips = findMemberedTripsByUser(user);
//        return trips
//                .stream()
//                .map(this::tripToTripDto)
//                .collect(Collectors.toList());
//    }
//
//    public List<TripOutputDto> findOrganizedTripsByUserLogin(String userLogin){
//        User user = findUserByLogin(userLogin);
//        List<Trip> trips = findOrganizedTripsByUser(user);
//        return trips.stream().map(this::tripToTripDto).collect(Collectors.toList());
//    }
//
//    public List<TripOutputDto> findParticipatedTripsByUserLogin(String userLogin){
//        User user = findUserByLogin(userLogin);
//        List<Trip> trips = findMemberedTripsByUser(user);
//        return trips.stream().map(this::tripToTripDto).collect(Collectors.toList());
//    }
//
//    public TripOutputDto addNewParticipantBy(Long tripId, Long userId){
//
//        User user = findUserById(userId);
//        Optional<Trip> tripOptional = Optional.of(tripRepository.findById(tripId)).orElseThrow(TripNotFoundException::new);
//        if (tripOptional.isEmpty()){
//            throw new TripNotFoundException();
//        }
//        tripOptional.get().addParticipant(user);
//        Trip trip = tripRepository.save(tripOptional.get());
//
//        return tripToTripDto(trip);
//
//    }
//
//    private User findUserById(Long userId){
//        Optional<User> user = Optional.of(userRepository.findUserByUserId(userId)).orElseThrow(MyUserNotFoundException::new);
//        if (user.isEmpty()) {
//            throw new MyUserNotFoundException();
//        }
//        return user.get();
//    }
//
//    private List<Trip> findOrganizedTripsByUser(User user) {
//        Optional<List<Trip>> trips = Optional.of(tripRepository.findByOrganizersContaining(user).orElseThrow(TripNotFoundException::new));
//        return trips.get();
//    }
//
//    private List<Trip> findMemberedTripsByUser(User user) {
//        Optional<List<Trip>> trips = Optional.of(tripRepository.findByMembersContaining(user).orElseThrow(TripNotFoundException::new));
//        return trips.get();
//    }
//
//    private User findUserByLogin(String userLogin){
//        Optional<User> user = Optional.of(userRepository.findUserByUserLogin(userLogin)).orElseThrow(MyUserNotFoundException::new);
//        if (user.isEmpty()) {
//            throw new MyUserNotFoundException();
//        }
//        return user.get();
//    }
//
//    private TripOutputDto tripToTripDto(Trip trip) {
//        return new TripOutputDto(
//                trip.getTripId(),
//                trip.getTripName(),
//                trip.getTripDescription(),
//                trip.getStartDate(),
//                trip.getEndDate(),
//                trip.getLocation()
//                        .getLocationName(),
//                trip.getLocation()
//                        .getLocationDescription(),
//                trip.getLocation()
//                        .getGoogleMapUrl(),
//                trip.getOrganizers()
//                        .stream()
//                        .map(User::userToUserDetailsDto)
//                        .collect(Collectors.toList()),
//                trip.getMembers()
//                        .stream()
//                        .map(User::userToUserDetailsDto)
//                        .collect(Collectors.toList()));
//    }

    private Trip createTrip(Tuple2<UserEntity, Location> userAndLocationEntities, TripCreateDto tripCreateDto){
        Trip trip = modelMapper.map(tripCreateDto, Trip.class);
        trip.addOrganizer(userAndLocationEntities._1);
        trip.setLocation(userAndLocationEntities._2);
        trip.addDateProposition(createDateProposition(tripCreateDto, userAndLocationEntities._1));
        return trip;
    }

    private Validation<Error, Tuple2<UserEntity, Location>> isUserAndLocationExists (TripCreateDto tripCreateDto){

        String errorMessage = "";
        boolean isNotExist = false;

        String loggedUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();

        Optional<UserEntity> userEntityOptional = userRepository.findByUsername(loggedUser);
        Optional<Location> locationOptional = locationRepository.findById(tripCreateDto.getLocationId());

        if (userEntityOptional.isEmpty()){
            isNotExist = true;
            errorMessage = "USER_NOT_EXISTS";
        }
        if (locationOptional.isEmpty()){
            isNotExist = true;
            if(errorMessage.isEmpty()){
                errorMessage = "LOCATION_NOT_EXISTS";
            } else {
                errorMessage = "LOCATION_AND" + errorMessage;
            }
        }

        return !isNotExist ?
                Validation.valid(Tuple.of(
                        userEntityOptional.get(),
                        locationOptional.get()
                )) : Validation.invalid(Error.badRequest(errorMessage));

    }

    private DateProposition createDateProposition(TripCreateDto tripCreateDto, UserEntity userEntity){
        return new DateProposition(
                LocalDate.parse(tripCreateDto.getStartDate()),
                LocalDate.parse(tripCreateDto.getEndDate()),
                userEntity.getUsername(),
                userEntity.getId());
    }

}
