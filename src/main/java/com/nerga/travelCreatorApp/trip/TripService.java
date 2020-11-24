package com.nerga.travelCreatorApp.trip;


import com.nerga.travelCreatorApp.common.response.Response;
import com.nerga.travelCreatorApp.location.exceptions.LocationNotFoundException;
import com.nerga.travelCreatorApp.security.auth.database.UserRepository;
import com.nerga.travelCreatorApp.trip.dto.TripCreateDto;
import com.nerga.travelCreatorApp.trip.dto.TripDetailsDto;
import com.nerga.travelCreatorApp.trip.exceptions.TripCannotBeCreatedException;
import com.nerga.travelCreatorApp.trip.exceptions.TripNotFoundException;
import com.nerga.travelCreatorApp.security.auth.exceptions.MyUserNotFoundException;
import com.nerga.travelCreatorApp.location.Location;
import com.nerga.travelCreatorApp.location.LocationRepository;

import org.springframework.beans.factory.annotation.Autowired;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//@Service("tripService")
public class TripService {

    private final TripRepository tripRepository;
    private final LocationRepository locationRepository;
    private final UserRepository userRepository;

    @Autowired
    public TripService(TripRepository tripRepository,
                       LocationRepository locationRepository,
                       UserRepository userRepository
    ) {
        this.tripRepository = tripRepository;
        this.locationRepository = locationRepository;
        this.userRepository = userRepository;
    }

    public Response addTrip (TripCreateDto tripCreateDto) {

        return null;

    }

//    public List<TripDetailsDto> findAllTrips() {
//        Optional<List<Trip>> optionalTripList = Optional.of(tripRepository.findAll());
//        return optionalTripList.get()
//                .stream()
//                .map(this::tripToTripDto)
//                .collect(Collectors.toList());
//    }
//
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

}
