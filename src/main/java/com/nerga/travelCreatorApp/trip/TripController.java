//package com.nerga.travelCreatorApp.trip;
//
//import com.nerga.travelCreatorApp.trip.exceptions.TripException;
//import com.nerga.travelCreatorApp.security.auth.exceptions.UserException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;

//@RestController
//@RequestMapping("/trip")
//public class TripController {
//    private final TripService tripService;
//
//    @Autowired
//    public TripController(TripService tripService) {
//        this.tripService = tripService;
//    }
//
//    @PostMapping(path = "test")
//    @ResponseBody
//    public String test() {
//        return "Trip Test Done";
//    }
//
//    @PostMapping(path = "createTrip")
//    @ResponseStatus
//    public ResponseEntity createTrip(@RequestBody TripCreateDto tripCreateDto) {
//        try {
//            tripService.addTrip(tripCreateDto);
//            return new ResponseEntity(HttpStatus.CREATED);
//        } catch (TripException e) {
//            return new ResponseEntity(HttpStatus.CONFLICT);
//        }
//    }
//
//    @GetMapping(path = "findAll")
//    public ResponseEntity findAll() {
//        List<TripOutputDto> test = tripService.findAllTrips();
//        return ResponseEntity.ok(test);
//    }
//
//    @GetMapping(path = "findById/{id}")
//    public ResponseEntity findById(@PathVariable("id") Long tripId) {
//        try {
//            TripOutputDto trip = tripService.findTripById(tripId);
//            return ResponseEntity.ok(trip);
//        } catch (TripException e) {
//            return new ResponseEntity(HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @GetMapping(path = "findAllOrganizedTripsByUserId/{userid}")
//    public ResponseEntity findAllUserOrganizedTripsByUserId(@PathVariable("userid") Long userId) {
//        try {
//            List<TripOutputDto> userTrips = tripService.findOrganizedTripsByUserId(userId);
//            return ResponseEntity.ok(userTrips);
//        } catch (TripException | UserException e) {
//            return new ResponseEntity(HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @GetMapping(path = "findAllMemberedTripsByUserId/{userId}")
//    public ResponseEntity findAllUserMemberedTripsByUserLogin(@PathVariable("userId") Long userId){
//        try {
//            List<TripOutputDto> userTrips = tripService.findParticipatedTripsByUserId(userId);
//            return ResponseEntity.ok(userTrips);
//        } catch (TripException | UserException e) {
//            return new ResponseEntity(HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @PutMapping(path="addNewParticipantToTrip/{tripId}/{userId}")
//    public ResponseEntity addNewParticipantToTrip(
//            @PathVariable("tripId") Long tripId,
//            @PathVariable("userId") Long userId) {
//        System.out.println(tripId);
//        System.out.println(userId);
//        try {
//           TripOutputDto trip = tripService.addNewParticipantBy(tripId, userId);
//           return ResponseEntity.ok(trip);
//        }  catch (TripException | UserException e) {
//        return new ResponseEntity(HttpStatus.NOT_FOUND);
//        }
//    }
//
//}
