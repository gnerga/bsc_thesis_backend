package com.nerga.travelCreatorApp.trip;

import com.nerga.travelCreatorApp.common.response.Error;
import com.nerga.travelCreatorApp.common.response.Response;
import com.nerga.travelCreatorApp.common.response.Success;
import com.nerga.travelCreatorApp.expensesregister.ExpenseRecord;
import com.nerga.travelCreatorApp.expensesregister.Expense;
import com.nerga.travelCreatorApp.expensesregister.ExpensesRepository;
import com.nerga.travelCreatorApp.expensesregister.dto.ExpenseRecordDetailsDto;
import com.nerga.travelCreatorApp.expensesregister.dto.ExpensesDetailsDto;
import com.nerga.travelCreatorApp.expensesregister.dto.ExpensesSummaryDto;
import com.nerga.travelCreatorApp.expensesregister.exceptions.ExpenseNotFoundException;
import com.nerga.travelCreatorApp.location.LocationRepository;
import com.nerga.travelCreatorApp.location.dto.LocationDetailsDto;
import com.nerga.travelCreatorApp.post.Post;
import com.nerga.travelCreatorApp.post.dto.PostDetailsDto;
import com.nerga.travelCreatorApp.security.auth.database.UserEntity;
import com.nerga.travelCreatorApp.security.auth.database.UserRepository;
import com.nerga.travelCreatorApp.security.auth.exceptions.CustomUserNotFoundException;
import com.nerga.travelCreatorApp.security.auth.exceptions.UserException;
import com.nerga.travelCreatorApp.security.dto.UserDetailsDto;
import com.nerga.travelCreatorApp.security.dto.UserSummaryDto;
import com.nerga.travelCreatorApp.trip.dto.TripDetailsDto;
import com.nerga.travelCreatorApp.trip.dto.TripDetailsForListViewDto;
import com.nerga.travelCreatorApp.trip.dto.TripSummaryDto;
import com.nerga.travelCreatorApp.trip.exceptions.TripException;
import com.nerga.travelCreatorApp.trip.exceptions.TripNotFoundException;
import io.vavr.control.Option;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TripGeneralService {

    TripRepository tripRepository;
    LocationRepository locationRepository;
    UserRepository userRepository;
    ExpensesRepository expensesRepository;
    ModelMapper modelMapper;

    @Autowired
    public TripGeneralService(
            TripRepository tripRepository,
            LocationRepository locationRepository,
            UserRepository userRepository,
            ExpensesRepository expensesRepository,
            ModelMapper modelMapper
    ) {
        this.tripRepository = tripRepository;
        this.locationRepository = locationRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.expensesRepository = expensesRepository;

    }

    public Response findAllTripsOrganizedByLoggedUser(){

        String loggedUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        UserEntity user;
        try{
            user = Option.ofOptional(userRepository.findByUsername(loggedUser))
                    .getOrElseThrow(()->new CustomUserNotFoundException("USER_NOT_FOUND"));
        } catch (UserException e) {
            return Error.notFound("USER_NOT_FOUND");
        }

        List<Trip> organizedTrip = user.getOrganizedTrips();

        return Success.ok(mapTripListToTripDetailsForListViewDto(organizedTrip));

    }

    public Response findAllTripsParticipatedByLoggedUser(){
        String loggedUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        UserEntity user;
        try{
            user = Option.ofOptional(userRepository.findByUsername(loggedUser))
                    .getOrElseThrow(()->new CustomUserNotFoundException("USER_NOT_FOUND"));
        } catch (UserException e) {
            return Error.notFound("USER_NOT_FOUND");
        }

        List<Trip> participatedTrips = user.getParticipatedTrips();

        return Success.ok(mapTripListToTripDetailsForListViewDto(participatedTrips));
    }

    public Response getAllTripOrganizers(Long tripId){
        Trip trip;
        try {
            trip = Option.ofOptional(tripRepository.findById(tripId))
                    .getOrElseThrow(()->new TripNotFoundException("TRIP_NOT_FOUND"));
        } catch (TripException e) {
            return Error.notFound("TRIP_NOT_FOUND");
        }

        return Success.ok(mapUserEntitiesListToUserDetailsDtoList(trip.getOrganizers()));
    }

    public Response getAllTripParticipants(Long tripId){
        Trip trip;
        try {
            trip = Option.ofOptional(tripRepository.findById(tripId))
                    .getOrElseThrow(()->new TripNotFoundException("TRIP_NOT_FOUND"));
        } catch (TripException e) {
            return Error.notFound("TRIP_NOT_FOUND");
        }

        return Success.ok(mapUserEntitiesListToUserDetailsDtoList(trip.getParticipants()));
    }

    public Response getAllTripParticipantsAndOrganizers(Long tripId){
        Trip trip;
        try{
            trip = Option.ofOptional(tripRepository.findById(tripId))
                    .getOrElseThrow(() -> new TripNotFoundException("TRIP_NOT_FOUND"));
        } catch (Exception e) {
            return Error.notFound("TRIP_NOT_FOUND");
        }

        return Success.ok(mapAndMergeUserEntitiesListToUserDetailsDtoList(trip.getOrganizers(), trip.getParticipants()));

    }

    public Response getAllParticipantsNotIncludedToExpenseWithGivenId(Long tripId, Long expenseId){
        Trip trip;
        Expense expense;

        try{
            trip = Option.ofOptional(tripRepository.findById(tripId)).getOrElseThrow(() -> new TripNotFoundException("TRIP_NOT_FOUND"));
        } catch (Exception e){
            return Error.notFound("TRIP_NOT_FOUND");
        }

        try{
            expense = Option.ofOptional(expensesRepository.findById(expenseId)).getOrElseThrow(() -> new ExpenseNotFoundException("EXPENSE_NOT_FOUND"));
        } catch (Exception e){
            return Error.notFound("EXPENSE_NOT_FOUND");
        }

        List<UserEntity> participants = new ArrayList<>();
        List<UserEntity> shareHolders = new ArrayList<>();


        participants.addAll(trip.getParticipants());
        participants.addAll(trip.getOrganizers());

        List<UserEntity> participantsNotIncludedToExpense = new ArrayList<>();

        expense.getShareholders().forEach(
                expenseRecord -> {
                    shareHolders.add(expenseRecord.getUserEntity());
                }
        );

        participants.forEach(participant->{
            if(!shareHolders.contains(participant)){
                participantsNotIncludedToExpense.add(participant);
            }
        });

        return Success.ok(mapUserEntitiesListToUserDetailsDtoList(participantsNotIncludedToExpense));

    }

    public Response getTripById(Long tripId){
        Trip trip;
        try {
            trip = Option.ofOptional(tripRepository.findById(tripId))
                    .getOrElseThrow(()->new TripNotFoundException("TRIP_NOT_FOUND"));
        } catch (TripException e) {
            return Error.notFound("TRIP_NOT_FOUND");
        }
        trip.runAnalysis();
        return Success.ok(tripToTripDetailsDto(trip));
    }

    public Response getTripReportById(Long tripId, boolean isExpensesIncluded){

        Trip trip;
        try {
            trip = Option.ofOptional(tripRepository.findById(tripId))
                    .getOrElseThrow(() -> new TripNotFoundException("TRIP_NOT_FOUND"));
        } catch (TripException e) {
            return Error.notFound("TRIP_NOT_FOUND");
        }

        return Success.ok(generateTripSummary(trip, isExpensesIncluded));
    }

    private TripSummaryDto generateTripSummary(Trip trip, boolean isExpenseRecordIncluded) {

        TripSummaryDto summary = new TripSummaryDto();

        summary.setTripName(trip.getTripName());
        summary.setTripDescription(trip.getTripDescription());
        summary.setStartDate(trip.getStartDate().toString());
        summary.setEndDate(trip.getEndDate().toString());
        summary.setCountry(trip.getLocation().getLocationAddress().getCountryName());
        summary.setAddress(
                trip.getLocation().getLocationAddress().getStreetNameAndNumber()
                        + "\n" +
                        trip.getLocation().getLocationAddress().getCityName()
                        + ", " +
                        trip.getLocation().getLocationAddress().getZipCode()
        );
        summary.setLocationDescription(trip.getLocation().getLocationDescription());

        summary.setTotalExpenses(trip.getExpenses().stream().mapToDouble(Expense::getCost).sum());
        summary.setExpenseReportIncluded(isExpenseRecordIncluded);

        List<UserSummaryDto> usersSummary = new ArrayList<>();
        List<ExpensesSummaryDto> expensesSummary = new ArrayList<>();

        trip.getOrganizers().forEach(organizer -> {
            usersSummary.add(new UserSummaryDto(
                    organizer.getUsername(),
                    organizer.getFirstName(),
                    organizer.getLastName(),
                    organizer.getEmail(),
                    organizer.getPhoneNumber()));
        });

        trip.getParticipants().forEach(participant -> {
            usersSummary.add(new UserSummaryDto(
                    participant.getUsername(),
                    participant.getFirstName(),
                    participant.getLastName(),
                    participant.getEmail(),
                    participant.getPhoneNumber()));
        });

        for (Expense  it: trip.getExpenses()){
            ExpensesSummaryDto expenseSummary = new ExpensesSummaryDto();
            expenseSummary.setExpenseName(it.getTitle());
            expenseSummary.setExpenseDescription(it.getDescription());
            double total = 0.0;
            for(ExpenseRecord record : it.getShareholders()){
                total += record.getAmount();
            }
            expenseSummary.setExpenseCost(total);
        }

        for(UserSummaryDto it: usersSummary) {
            double overallExpenses = 0.0;
            for(Expense expense: trip.getExpenses()) {
                for(ExpenseRecord record: expense.getShareholders()){
                    if(it.getUsername().equals(record.getUserEntity().getUsername())){
                        overallExpenses+=record.getAmount();
                    }
                }
            }
            it.setTotalExpensesCost(overallExpenses);
        }

        summary.setParticipants(usersSummary);
        summary.setExpensesSummary(expensesSummary);

        return summary;
    }

    private List<UserSummaryDto> createUserSummary(List<UserEntity> users){
        return null;
    }

    private List<ExpensesSummaryDto> createExpensesSummary(List<Expense> expenses){
        return null;
    }

    private TripDetailsDto tripToTripDetailsDto(Trip trip){

        return new TripDetailsDto(
                trip.getTripId(),
                trip.getTripName(),
                trip.getTripDescription(),
                trip.getTripLength(),
                trip.getStartDate(),
                trip.getEndDate(),
                modelMapper.map(trip.getLocation(), LocationDetailsDto.class),
                trip.getDateMatcherReport(),
                mapPostsToListPostDetailsDto(trip.getPosts()),
                mapExpensesListToExpensesDetailsDtoLost(trip.getExpenses()),
                mapUserEntitiesListToUserDetailsDtoList(trip.getOrganizers()),
                mapUserEntitiesListToUserDetailsDtoList(trip.getParticipants())

        );
    }

    private ExpensesDetailsDto mapExpensesToExpensesDetailsDto(Expense expense){
        List<ExpenseRecordDetailsDto> list = new ArrayList<>();

        for (ExpenseRecord it: expense.getShareholders()){
            UserDetailsDto user = modelMapper.map(it.getUserEntity(), UserDetailsDto.class);
            list.add(new ExpenseRecordDetailsDto(it.getExpenseRecordId(), user, it.getAmount()));
        }

        return new ExpensesDetailsDto(
                expense.getExpensesId(),
                expense.getTitle(),
                expense.getDescription(),
                expense.getCost(),
                list
        );
    }

    private List<ExpensesDetailsDto> mapExpensesListToExpensesDetailsDtoLost(List<Expense> expenseList){
        List<ExpensesDetailsDto> expensesDtoList = new ArrayList<>();
        for(Expense expense : expenseList) {
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
