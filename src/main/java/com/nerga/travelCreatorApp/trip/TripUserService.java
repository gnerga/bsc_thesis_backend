package com.nerga.travelCreatorApp.trip;

import com.nerga.travelCreatorApp.common.response.Error;
import com.nerga.travelCreatorApp.common.response.Response;
import com.nerga.travelCreatorApp.common.response.Success;
import com.nerga.travelCreatorApp.datepropositionmatcher.DateProposition;
import com.nerga.travelCreatorApp.datepropositionmatcher.dto.DatePropositionDto;
import com.nerga.travelCreatorApp.datepropositionmatcher.dto.DatePropositionReturnedListDto;
import com.nerga.travelCreatorApp.expensesregister.ExpenseRecord;
import com.nerga.travelCreatorApp.expensesregister.Expenses;
import com.nerga.travelCreatorApp.expensesregister.dto.*;
import com.nerga.travelCreatorApp.location.LocationRepository;
import com.nerga.travelCreatorApp.security.auth.database.UserEntity;
import com.nerga.travelCreatorApp.security.auth.database.UserRepository;
import com.nerga.travelCreatorApp.security.auth.exceptions.MyUserNotFoundException;
import com.nerga.travelCreatorApp.security.auth.exceptions.UserException;
import com.nerga.travelCreatorApp.security.dto.UserDetailsDto;
import io.vavr.control.Option;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TripUserService {

    TripRepository tripRepository;
    LocationRepository locationRepository;
    UserRepository userRepository;
    ModelMapper modelMapper;

    @Autowired
    public TripUserService(TripRepository tripRepository, LocationRepository locationRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.tripRepository = tripRepository;
        this.locationRepository = locationRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public Response createExpense(ExpensesCreateDto newExpenses) {

        Trip trip;
        try {
            trip = Option.ofOptional(tripRepository.findById(newExpenses.getTripId()))
                    .getOrElseThrow(() -> new MyUserNotFoundException("TRIP_NOT_FOUND"));
        } catch (UserException e) {
            return Error.notFound("TRIP_NOT_FOUND");
        }

       if(checkIfAllUserExists(newExpenses.getShareHolders())){
           return Error.notFound("USER_NOT_FOUND");
       }

       Expenses expense = mapExpensesCreateDto(newExpenses);

       trip.getExpenseManager().addExpenses(expense);

       trip = tripRepository.save(trip);

       return Success.ok(mapExpensesToExpensesDetailsDto(expense));
    }

    public Response updateExpenseById(ExpenseUpdateDto expenseUpdateDto) {

        Trip trip;

        try {
            trip = Option.ofOptional(tripRepository.findById(expenseUpdateDto.getTripId()))
                    .getOrElseThrow(() -> new MyUserNotFoundException("TRIP_NOT_FOUND"));
        } catch (UserException e) {
            return Error.notFound("TRIP_NOT_FOUND");
        }

        if (!trip.getExpenseManager().updateExpenses(expenseUpdateDto)){
            return Error.notFound("EXPENSE_NOT_FOUND");
        }

        trip = tripRepository.save(trip);


        return Success.ok(mapExpensesToExpensesDetailsDto(
                trip
                        .getExpenseManager()
                        .findExpenses(expenseUpdateDto
                                .getExpenseId())));
    }



    public Response addUserToExpenseByUserIdAndTripId(ExpenseRecordCreateDto newRecord, Long tripId) {
        Trip trip;
        return null;
    }

    public Response updateExpenseShareholdersAmount() {
        return null;
    }


    public Response addPostByTripId() {
        return null;
    }

    public Response handUpByTripAndPostId() {
        return null;
    }

    public Response handDownByTripAndPostId() {
        return null;
    }

//    public Response leaveTripWithGivenId() {
//        return null;
//    }


    public Response addNewDateProposition(DatePropositionDto datePropositionDto, Long tripId) {

        Trip trip;
        try {
            trip = Option.ofOptional(tripRepository.findById(tripId))
                    .getOrElseThrow(() -> new MyUserNotFoundException("USER_NOT_FOUND"));
        } catch (UserException e) {
            return Error.notFound("USER_NOT_FOUND");
        }


        DateProposition proposition = modelMapper.map(datePropositionDto, DateProposition.class);

        trip.addDateProposition(proposition);
        trip.getDatePropositionMatcher().runAnalysis();

        DatePropositionReturnedListDto report = trip.getDatePropositionMatcher().getDateMatcherReport();

        tripRepository.save(trip);

        return Success.ok(report);

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

    private boolean checkIfAllUserExists(List<ExpenseRecordCreateDto> records){
        for (ExpenseRecordCreateDto it : records) {
            if (!userRepository.existsById(it.getUserId())){;
                return true;
            }
        }
        return false;
    }

    private Expenses mapExpensesCreateDto(ExpensesCreateDto newExpenses){

        List<ExpenseRecord> records = new ArrayList<>();
        Optional<UserEntity> optional;

        for (ExpenseRecordCreateDto it : newExpenses.getShareHolders()) {

            optional = userRepository.findById(it.getUserId());

            if(optional.isEmpty()){
                return null;
            }

            records.add(new ExpenseRecord(optional.get(), it.getAmount()));
        }

        Expenses expenses = new Expenses(
                newExpenses.getTitle(),
                newExpenses.getDescription(),
                newExpenses.getCost(),
                records
        );

        return expenses;
    }

}
