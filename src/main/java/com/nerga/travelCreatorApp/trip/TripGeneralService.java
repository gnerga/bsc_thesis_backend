package com.nerga.travelCreatorApp.trip;

import com.nerga.travelCreatorApp.common.response.Response;
import com.nerga.travelCreatorApp.location.LocationRepository;
import com.nerga.travelCreatorApp.security.auth.database.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class TripGeneralService {

    TripRepository tripRepository;
    LocationRepository locationRepository;
    UserRepository userRepository;
    ModelMapper modelMapper;

    @Autowired
    public TripGeneralService(TripRepository tripRepository, LocationRepository locationRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.tripRepository = tripRepository;
        this.locationRepository = locationRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public Response findAllTripsOrganizedByUserId(){
        return null;
    }
    public Response findAllTripsParticipatedByUserId(){
        return null;
    }

    public Response getAllTripOrganizers(){return  null;}
    public Response getAllTripParticipants(){return null;}

    public Response getOrganizedTripById(){return null;}
    public Response getParticipatedTripById(){return null;}

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