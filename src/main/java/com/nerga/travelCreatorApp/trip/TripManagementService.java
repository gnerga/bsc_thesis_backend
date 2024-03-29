package com.nerga.travelCreatorApp.trip;


import com.nerga.travelCreatorApp.common.response.Error;
import com.nerga.travelCreatorApp.common.response.Response;
import com.nerga.travelCreatorApp.common.response.Success;
import com.nerga.travelCreatorApp.datepropositionmatcher.DateProposition;
import com.nerga.travelCreatorApp.datepropositionmatcher.DatePropositionRepository;
import com.nerga.travelCreatorApp.expensesregister.ExpenseRecord;
import com.nerga.travelCreatorApp.expensesregister.ExpenseRecordRepository;
import com.nerga.travelCreatorApp.expensesregister.Expenses;
import com.nerga.travelCreatorApp.expensesregister.ExpensesRepository;
import com.nerga.travelCreatorApp.expensesregister.dto.ExpenseRecordDetailsDto;
import com.nerga.travelCreatorApp.expensesregister.dto.ExpensesDetailsDto;
import com.nerga.travelCreatorApp.location.Location;
import com.nerga.travelCreatorApp.location.dto.LocationDetailsDto;
import com.nerga.travelCreatorApp.post.Post;
import com.nerga.travelCreatorApp.post.dto.PostDetailsDto;
import com.nerga.travelCreatorApp.security.auth.database.UserEntity;
import com.nerga.travelCreatorApp.security.auth.database.UserRepository;
import com.nerga.travelCreatorApp.security.auth.exceptions.CustomUserNotFoundException;
import com.nerga.travelCreatorApp.security.auth.exceptions.UserException;
import com.nerga.travelCreatorApp.security.dto.UserDetailsDto;
import com.nerga.travelCreatorApp.trip.dto.*;
import com.nerga.travelCreatorApp.location.LocationRepository;

import com.nerga.travelCreatorApp.trip.exceptions.TripException;
import com.nerga.travelCreatorApp.trip.exceptions.TripNotFoundException;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Option;
import io.vavr.control.Validation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service("tripManagementService")
public class TripManagementService {

    private final TripRepository tripRepository;
    private final LocationRepository locationRepository;
    private final UserRepository userRepository;
    private final DatePropositionRepository datePropositionRepository;
    private final ExpensesRepository expensesRepository;
    private final ExpenseRecordRepository expenseRecordRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TripManagementService(TripRepository tripRepository,
                                 LocationRepository locationRepository,
                                 UserRepository userRepository,
                                 ModelMapper modelMapper,
                                 ExpensesRepository expensesRepository,
                                 ExpenseRecordRepository expenseRecordRepository,
                                 DatePropositionRepository datePropositionRepository) {
        this.tripRepository = tripRepository;
        this.locationRepository = locationRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.datePropositionRepository = datePropositionRepository;
        this.expenseRecordRepository = expenseRecordRepository;
        this.expensesRepository = expensesRepository;
    }

    public Response addTrip(TripCreateDto tripCreateDto){
        return isUserAndLocationExists(tripCreateDto)
                .map(userEntityAndLocation -> createTrip(userEntityAndLocation, tripCreateDto))
                .map(tripRepository::save)
                .map(returnedTrip -> modelMapper.map(returnedTrip, TripUserAndDetailsDto.class))
                .fold(Function.identity(), Success::ok);
    }

    public Response findAllTrips(){
        List<Trip> tripList = tripRepository.findAll();
           return !tripList.isEmpty() ? Success.ok(convertListDetailsDto(tripList)) : Success.ok(new ArrayList<TripUserAndDetailsDto>());
    }

    public Response addNewOrganizerById(Long tripId, Long userId){
        Trip trip;
        UserEntity newOrganizer;

        try {
            newOrganizer = Option.ofOptional(userRepository.findById(userId))
                    .getOrElseThrow(()->new CustomUserNotFoundException("USER_NOT_FOUND"));
        } catch (UserException e) {
            return Error.notFound("USER_NOT_FOUND");
        }

        try {
            trip = Option.ofOptional(tripRepository.findById(tripId))
                    .getOrElseThrow(()->new TripNotFoundException("TRIP_NOT_FOUND"));
        } catch (TripException e) {
            return Error.notFound("TRIP_NOT_FOUND");
        }

        trip.addOrganizer(newOrganizer);
        trip.removeParticipant(newOrganizer);
        trip = tripRepository.save(trip);
        userRepository.save(newOrganizer);

//        TripUserAndDetailsDto tripUserAndDetailsDto = modelMapper.map(trip, TripUserAndDetailsDto.class);
        TripUserAndDetailsDto tripUserAndDetailsDto = tripToTripDetailsDto(trip);

        return Success.ok(tripUserAndDetailsDto);

    }

    public Response removeOrganizerById(Long tripId, Long userId){
        Trip trip;
        UserEntity newOrganizer;

        try {
            newOrganizer = Option.ofOptional(userRepository.findById(userId))
                    .getOrElseThrow(()->new CustomUserNotFoundException("USER_NOT_FOUND"));
        } catch (UserException e) {
            return Error.notFound("USER_NOT_FOUND");
        }

        try {
            trip = Option.ofOptional(tripRepository.findById(tripId))
                    .getOrElseThrow(()->new TripNotFoundException("TRIP_NOT_FOUND"));
        } catch (TripException e) {
            return Error.notFound("TRIP_NOT_FOUND");
        }

        trip.removeOrganizer(newOrganizer);
        trip.addParticipant(newOrganizer);
        trip = tripRepository.save(trip);
        newOrganizer = userRepository.save(newOrganizer);


//        TripUserAndDetailsDto tripUserAndDetailsDto = modelMapper.map(trip, TripUserAndDetailsDto.class);
        TripUserAndDetailsDto tripUserAndDetailsDto = tripToTripDetailsDto(trip);
        return Success.ok(tripUserAndDetailsDto);
    }

    public Response addNewParticipantById(Long tripId, Long userId){
        Trip trip;
        UserEntity newParticipant;

        try {
            newParticipant = Option.ofOptional(userRepository.findById(userId))
                    .getOrElseThrow(()->new CustomUserNotFoundException("USER_NOT_FOUND"));
        } catch (UserException e) {
            return Error.notFound("USER_NOT_FOUND");
        }

        try {
            trip = Option.ofOptional(tripRepository.findById(tripId))
                    .getOrElseThrow(()->new TripNotFoundException("TRIP_NOT_FOUND"));
        } catch (TripException e) {
            return Error.notFound("TRIP_NOT_FOUND");
        }

        if (
                trip.getParticipants().contains(newParticipant) ||
                        trip.getOrganizers().contains(newParticipant)
        ) {
            return Error.badRequest("GIVEN_USER_ALREADY_IS_TRIP_PARTICIPANT");
        }


        trip.addParticipant(newParticipant);
        trip = tripRepository.save(trip);
        userRepository.save(newParticipant);

//        TripUserAndDetailsDto tripUserAndDetailsDto = modelMapper.map(trip, TripUserAndDetailsDto.class);
        TripUserAndDetailsDto tripUserAndDetailsDto = tripToTripDetailsDto(trip);
        return Success.accepted(tripUserAndDetailsDto);
    }

    public Response removeParticipantById(Long tripId, Long userId){
        Trip trip;
        UserEntity participant;

        try {
            participant = Option.ofOptional(userRepository.findById(userId))
                    .getOrElseThrow(()->new CustomUserNotFoundException("USER_NOT_FOUND"));
        } catch (UserException e) {
            return Error.notFound("USER_NOT_FOUND");
        }

        try {
            trip = Option.ofOptional(tripRepository.findById(tripId))
                    .getOrElseThrow(()->new TripNotFoundException("TRIP_NOT_FOUND"));
        } catch (TripException e) {
            return Error.notFound("TRIP_NOT_FOUND");
        }

        if (trip.getParticipants().contains(participant)) {
            removeUserRecordsFromExpenses(participant.getId(), trip);
            trip.removeParticipant(participant);
        } else if (trip.getOrganizers().contains(participant)) {
            removeUserRecordsFromExpenses(participant.getId(), trip);
            trip.removeOrganizer(participant);
        } else {
            return Error.notFound("USER_NOT_PARTICIPATED_IN_TRIP");
        }

        trip = tripRepository.save(trip);
        participant = userRepository.save(participant);

//        TripUserAndDetailsDto tripUserAndDetailsDto = modelMapper.map(trip, TripUserAndDetailsDto.class);
        TripUserAndDetailsDto tripUserAndDetailsDto = tripToTripDetailsDto(trip);
        return Success.ok(tripUserAndDetailsDto);
    }

    public Response updateTrip(TripUpdateDto update){

        Trip trip;

        try {
            trip = Option.ofOptional(tripRepository.findById(update.getTripId()))
                    .getOrElseThrow(()->new TripNotFoundException("TRIP_NOT_FOUND"));
        } catch (TripException e) {
            return Error.notFound("TRIP_NOT_FOUND");
        }

        trip.updateTripFromTripUpdateDto(update);

        trip = tripRepository.save(trip);

//        TripUserAndDetailsDto tripUserAndDetailsDto = modelMapper.map(trip, TripUserAndDetailsDto.class);
        TripUserAndDetailsDto tripUserAndDetailsDto = tripToTripDetailsDto(trip);
        return Success.ok(tripUserAndDetailsDto);
    }

    public Response changeTripDate(Long tripId) {
        Trip trip;
        try {
            trip = Option.ofOptional(tripRepository.findById(tripId))
                    .getOrElseThrow(()->new TripNotFoundException("TRIP_NOT_FOUND"));
        } catch (TripException e) {
            return Error.notFound("TRIP_NOT_FOUND");
        }

        trip = trip.updateDateBasedOnBestMatch();

        trip = tripRepository.save(trip);

//        TripUserAndDetailsDto tripUserAndDetailsDto = modelMapper.map(trip, TripUserAndDetailsDto.class);
        TripUserAndDetailsDto tripUserAndDetailsDto = tripToTripDetailsDto(trip);

        return Success.ok(tripUserAndDetailsDto);

    }

    public Response resetDatePropositions(Long tripId){
        Trip trip;
        try {
            trip = Option.ofOptional(tripRepository.findById(tripId))
                    .getOrElseThrow(()->new TripNotFoundException("TRIP_NOT_FOUND"));
        } catch (TripException e) {
            return Error.notFound("TRIP_NOT_FOUND");
        }

        String loggedUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        UserEntity userEntity;
        try {
            userEntity = Option.ofOptional(userRepository.findByUsername(loggedUser))
                    .getOrElseThrow(()->new CustomUserNotFoundException("USER_NOT_FOUND"));
        } catch (UserException e) {
            return Error.notFound("USER_NOT_FOUND");
        }
        List<DateProposition> l1 = List.copyOf(trip.getDatePropositionList());

        trip.getDatePropositionList().clear();
        trip.getAnalyzedDatePropositionList().clear();

        trip = tripRepository.save(trip);
        l1.forEach(datePropositionRepository::delete);

        DateProposition dateProposition = new DateProposition(trip.getStartDate(), trip.getEndDate(), userEntity.getId());
        datePropositionRepository.save(dateProposition);
        trip.addDateProposition(dateProposition);
        trip = tripRepository.save(trip);

        TripUserAndDetailsDto tripUserAndDetailsDto = tripToTripDetailsDto(trip);

        return Success.ok(tripUserAndDetailsDto);

    }

    private void  removeUserRecordsFromExpenses(Long userId, Trip trip){

        if(!trip.getExpenses().isEmpty() || trip.getExpenses() == null){

            for(Expenses expense: trip.getExpenses()){
                for (ExpenseRecord record : expense.getShareholders()){
                    if(record.getUserEntity().getId().equals(userId)){
                        expense.setCost(expense.getCost() - record.getAmount());
                        expense.getShareholders().remove(record);
                        expense = expensesRepository.save(expense);
                        expenseRecordRepository.delete(record);
                        return;
                    }
                }

            }
        }
    }

    private List<TripUserAndDetailsDto> convertListDetailsDto(List<Trip> trips){
        return trips.stream().map(trip -> modelMapper.map(trip, TripUserAndDetailsDto.class)).collect(Collectors.toList());
    }

    private Trip createTrip(Tuple2<UserEntity, Location> userAndLocationEntities, TripCreateDto tripCreateDto){
        Trip trip = modelMapper.map(tripCreateDto, Trip.class);
        trip.addOrganizer(userAndLocationEntities._1);
        trip.setLocation(userAndLocationEntities._2);
        trip = tripRepository.save(trip);
        DateProposition proposition = datePropositionRepository.save(createDateProposition(tripCreateDto, userAndLocationEntities._1));
        trip.addDateProposition(proposition);
        trip = tripRepository.save(trip);
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
                userEntity.getId());
    }

    private TripUserAndDetailsDto tripToTripDetailsDto(Trip trip){

        return new TripUserAndDetailsDto(
                trip.getTripId(),
                trip.getTripName(),
                trip.getTripDescription(),
                trip.getTripLength(),
                trip.getStartDate(),
                trip.getEndDate(),
                modelMapper.map(trip.getLocation(), LocationDetailsDto.class),
                mapUserEntitiesListToUserDetailsDtoList(trip.getOrganizers()),
                mapUserEntitiesListToUserDetailsDtoList(trip.getParticipants()),
                trip.getDateMatcherReport(),
                mapPostsToListPostDetailsDto(trip.getPosts()),
                mapExpensesListToExpensesDetailsDtoLost(trip.getExpenses())


        );
    }

    private ExpensesDetailsDto mapExpensesToExpensesDetailsDto(Expenses expenses){
        List<ExpenseRecordDetailsDto> list = new ArrayList<>();

        for (ExpenseRecord it: expenses.getShareholders()){
            UserDetailsDto user = modelMapper.map(it.getUserEntity(), UserDetailsDto.class);
            list.add(new ExpenseRecordDetailsDto(it.getExpenseRecordId(), user, it.getAmount()));
        }

        return new ExpensesDetailsDto(
                expenses.getExpensesId(),
                expenses.getTitle(),
                expenses.getDescription(),
                expenses.getCost(),
                list
        );
    }

    private List<ExpensesDetailsDto> mapExpensesListToExpensesDetailsDtoLost(List<Expenses> expensesList){
        List<ExpensesDetailsDto> expensesDtoList = new ArrayList<>();
        for(Expenses expense : expensesList) {
            expensesDtoList.add(mapExpensesToExpensesDetailsDto(expense));
        }
        return expensesDtoList;
    }

    private List<PostDetailsDto> mapPostsToListPostDetailsDto(List<Post> posts){
        List<Post> sortedPosts = posts.stream()
                .sorted(Comparator.comparing(Post::getTimeStamp).reversed())
                .collect(Collectors.toList());
        List<PostDetailsDto> list = new ArrayList<>();
        for (Post it: sortedPosts){
            list.add(new PostDetailsDto(
                    it.getPostId(),
                    it.getTitle(),
                    it.getContent(),
                    it.getTimeStamp(),
                    modelMapper.map(it.getAuthor(), UserDetailsDto.class),
                    it.getNumberOfLikes(),
                    it.getNumberOfDislikes(),
                    it.getLikes(),
                    it.getDislikes()

            ));
        }
        return list;
    }

    private List<UserDetailsDto> mapUserEntitiesListToUserDetailsDtoList(List<UserEntity> users){

        List<UserDetailsDto> list = new ArrayList<>();
        for(UserEntity it: users){
            list.add(modelMapper.map(it, UserDetailsDto.class));
        }
        return list;
    }

    private List<UserDetailsDto> mapAndMergeUserEntitiesListToUserDetailsDtoList(List<UserEntity> organizer, List<UserEntity> participants){

        List<UserDetailsDto> list = new ArrayList<>();
        for(UserEntity it: organizer){
            list.add(modelMapper.map(it, UserDetailsDto.class));
        }
        for(UserEntity it: participants){
            list.add(modelMapper.map(it, UserDetailsDto.class));
        }
        return list;
    }

    private List<TripDetailsForListViewDto> mapTripListToTripDetailsForListViewDto(List<Trip> trips){
        List<TripDetailsForListViewDto> list = new ArrayList<>();
        for (Trip it: trips) {
            list.add(new TripDetailsForListViewDto(
                    it.getTripId(),
                    it.getTripName(),
                    it.getLocation().getLocationName()
            ));
        }
        return list;
    }

}
