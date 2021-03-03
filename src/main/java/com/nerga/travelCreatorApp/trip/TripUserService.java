package com.nerga.travelCreatorApp.trip;

import com.nerga.travelCreatorApp.common.response.Error;
import com.nerga.travelCreatorApp.common.response.Response;
import com.nerga.travelCreatorApp.common.response.Success;
import com.nerga.travelCreatorApp.datepropositionmatcher.DateProposition;
import com.nerga.travelCreatorApp.datepropositionmatcher.DatePropositionRepository;
import com.nerga.travelCreatorApp.datepropositionmatcher.dto.DatePropositionDto;
import com.nerga.travelCreatorApp.datepropositionmatcher.dto.DatePropositionReturnedListDto;
import com.nerga.travelCreatorApp.expensesregister.ExpenseRecord;
import com.nerga.travelCreatorApp.expensesregister.ExpenseRecordRepository;
import com.nerga.travelCreatorApp.expensesregister.Expense;
import com.nerga.travelCreatorApp.expensesregister.ExpensesRepository;
import com.nerga.travelCreatorApp.expensesregister.dto.*;
import com.nerga.travelCreatorApp.location.LocationRepository;
import com.nerga.travelCreatorApp.post.Post;
import com.nerga.travelCreatorApp.post.PostRepository;
import com.nerga.travelCreatorApp.post.dto.PostCreateDto;
import com.nerga.travelCreatorApp.post.dto.PostDetailsDto;
import com.nerga.travelCreatorApp.post.exception.PostNotFoundException;
import com.nerga.travelCreatorApp.security.auth.database.UserEntity;
import com.nerga.travelCreatorApp.security.auth.database.UserRepository;
import com.nerga.travelCreatorApp.security.auth.exceptions.CustomUserNotFoundException;
import com.nerga.travelCreatorApp.security.auth.exceptions.UserException;
import com.nerga.travelCreatorApp.security.dto.UserDetailsDto;
import com.nerga.travelCreatorApp.trip.exceptions.TripException;
import com.nerga.travelCreatorApp.trip.exceptions.TripNotFoundException;
import io.vavr.control.Option;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TripUserService {

    TripRepository tripRepository;
    LocationRepository locationRepository;
    UserRepository userRepository;
    ModelMapper modelMapper;
    ExpensesRepository expensesRepository;
    ExpenseRecordRepository expenseRecordRepository;
    PostRepository postRepository;
    DatePropositionRepository datePropositionRepository;

    @Autowired
    public TripUserService(
                            TripRepository tripRepository,
                            LocationRepository locationRepository,
                            UserRepository userRepository,
                            ExpensesRepository expensesRepository,
                            ExpenseRecordRepository expenseRecordRepository,
                            DatePropositionRepository datePropositionRepository,
                            PostRepository postRepository,
                            ModelMapper modelMapper) {
        this.tripRepository = tripRepository;
        this.locationRepository = locationRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.expenseRecordRepository = expenseRecordRepository;
        this.expensesRepository = expensesRepository;
        this.postRepository = postRepository;
        this.datePropositionRepository = datePropositionRepository;
    }

    public Response createExpense(ExpensesCreateDto newExpenses) {

        Trip trip;

        try {
            trip = Option.ofOptional(tripRepository.findById(newExpenses.getTripId()))
                    .getOrElseThrow(() -> new CustomUserNotFoundException("TRIP_NOT_FOUND"));
        } catch (UserException e) {
            return Error.notFound("TRIP_NOT_FOUND");
        }

       if(checkIfAllUserExists(newExpenses.getShareHolders())){
           return Error.notFound("USER_NOT_FOUND");
       }

       Expense expense = mapExpensesCreateDto(newExpenses);

       trip.addExpense(expense);
       expense.setTrip(trip);
       trip = tripRepository.save(trip);
       expensesRepository.save(expense);




       return Success.ok(mapExpensesListToExpensesDetailsDtoLost(trip.getExpenses()));

    }

    public Response updateExpense(ExpenseUpdateDto expenseUpdateDto) {

        Expense expense;

        try {
            expense = Option.ofOptional(expensesRepository.findById(expenseUpdateDto.getExpenseId()))
                    .getOrElseThrow(() -> new CustomUserNotFoundException("EXPENSE_NOT_FOUND"));
        } catch (UserException e) {
            return Error.notFound("EXPENSE_NOT_FOUND");
        }

        expense = expense.updateFromExpensesUpdateDto(expenseUpdateDto);

        expense = expensesRepository.save(expense);


        return Success.ok(mapExpensesToExpensesDetailsDto(
                expense));
    }

    public Response addUserToExpense(ExpenseRecordCreateDto newRecord) {
        Expense expense;

        try {
            expense = Option.ofOptional(expensesRepository.findById(newRecord.getExpenseId()))
                    .getOrElseThrow(() -> new CustomUserNotFoundException("EXPENSE_NOT_FOUND"));
        } catch (UserException e) {
            return Error.notFound("EXPENSE_NOT_FOUND");
        }

        UserEntity user;

        try {
            user = Option.ofOptional(userRepository.findById(newRecord.getUserId()))
                    .getOrElseThrow(()->new CustomUserNotFoundException("USER_NOT_FOUND"));
        } catch (UserException e) {
            return Error.notFound("USER_NOT_FOUND");
        }

        ExpenseRecord record =  new ExpenseRecord(
                user,
                newRecord.getAmount()
        );

        record = expenseRecordRepository.save(record);
        expense.getShareholders().add(record);
        expense.setCost(expense.getCost() + record.getAmount());
        expense = expensesRepository.save(expense);

        System.out.println(expense.getCost());

        Trip trip;
        try {
            trip = Option.ofOptional(tripRepository.findById(expense.getTrip().getTripId()))
                    .getOrElseThrow(() -> new CustomUserNotFoundException("TRIP_NOT_FOUND"));
        } catch (UserException e) {
            return Error.notFound("TRIP_NOT_FOUND");
        }

        return Success.ok(mapExpensesListToExpensesDetailsDtoLost(trip.getExpenses()));
    }

    public Response removeUserFromExpense(ExpenseRecordCreateDto removeRecord) {
        System.out.println(removeRecord.getExpenseId());
        System.out.println(removeRecord.getUserId());

        Expense expense;

        try {
            expense = Option.ofOptional(expensesRepository.findById(removeRecord.getExpenseId()))
                    .getOrElseThrow(() -> new CustomUserNotFoundException("EXPENSE_NOT_FOUND"));
        } catch (UserException e) {
            return Error.notFound("EXPENSE_NOT_FOUND");
        }

        UserEntity user;

        try {
            user = Option.ofOptional(userRepository.findById(removeRecord.getUserId()))
                    .getOrElseThrow(()->new CustomUserNotFoundException("USER_NOT_FOUND"));
        } catch (UserException e) {
            return Error.notFound("USER_NOT_FOUND");
        }

        for (ExpenseRecord record : expense.getShareholders()){
            if(record.getUserEntity().getId().equals(user.getId())){
                expense.setCost(expense.getCost() - record.getAmount());
                expense.getShareholders().remove(record);
                expenseRecordRepository.delete(record);
            }
        }
        expense = expensesRepository.save(expense);

        Trip trip;
        try {
            trip = Option.ofOptional(tripRepository.findById(expense.getTrip().getTripId()))
                    .getOrElseThrow(() -> new CustomUserNotFoundException("TRIP_NOT_FOUND"));
        } catch (UserException e) {
            return Error.notFound("TRIP_NOT_FOUND");
        }

        return Success.ok(mapExpensesListToExpensesDetailsDtoLost(trip.getExpenses()));
    }

    public Response updateExpenseShareholders(ExpenseRecordsUpdateListDto updatedRecordList) {

        Expense expense;
        Trip trip;

        try {
            expense = Option.ofOptional(expensesRepository.findById(updatedRecordList.getExpenseId()))
                    .getOrElseThrow(() -> new CustomUserNotFoundException("EXPENSE_NOT_FOUND"));
        } catch (UserException e) {
            return Error.notFound("EXPENSE_NOT_FOUND");
        }

        for(ExpenseRecord it: expense.getShareholders()){
            for (ExpenseRecordsUpdateDto updatedRecord: updatedRecordList.getRecordsToUpdate()){
                if(updatedRecord.getUserId().equals(it.getUserEntity().getId())){
                    if(it.getAmount()!=updatedRecord.getAmount()){
                        it.setAmount(updatedRecord.getAmount());
                        expenseRecordRepository.save(it);
                    }
                }
            }
        }

        expense = expensesRepository.save(expense);

        double overallAmount = expense.getShareholders().stream().mapToDouble(ExpenseRecord::getAmount).sum();
        expense.setCost((float)overallAmount);
        expense = expensesRepository.save(expense);

        try {
            trip = Option.ofOptional(tripRepository.findById(updatedRecordList.getTripId()))
                    .getOrElseThrow(() -> new CustomUserNotFoundException("TRIP_NOT_FOUND"));
        } catch (UserException e) {
            return Error.notFound("TRIP_NOT_FOUND");
        }

        return Success.ok(mapExpensesListToExpensesDetailsDtoLost(trip.getExpenses()));
    }

    public Response addPostByTripId(PostCreateDto newPost, Long tripId) {
        Trip trip;
        UserEntity userEntity;

        try {
            trip = Option.ofOptional(tripRepository.findById(tripId))
                    .getOrElseThrow(()->new TripNotFoundException("TRIP_NOT_FOUND"));
        } catch (TripException e) {
            return Error.notFound("TRIP_NOT_FOUND");
        }

        try {
            userEntity = Option.ofOptional(userRepository.findById(newPost.getUserId()))
                    .getOrElseThrow(() -> new CustomUserNotFoundException("USER_NOT_FOUND"));
        } catch (UserException e) {
            return Error.notFound("USER_NOT_FOUND");
        }

        Post post = new Post(
                newPost.getTitle(),
                newPost.getContent(),
                LocalDateTime.parse(newPost.getTimeStamp()),
                userEntity
        );
        post.setTrip(trip);
        postRepository.save(post);
        trip.addPost(post);
        trip = tripRepository.save(trip);

        return Success.created(mapPostsToListPostDetailsDto(trip.posts));
    }

    public Response handUpByTripAndPostId(Long postId, Long userId) {
        Post post;

        try {
            post = Option.ofOptional(postRepository.findById(postId))
                    .getOrElseThrow(()->new PostNotFoundException("POST_NOT_FOUND"));
        } catch (TripException e) {
            return Error.notFound("POST_NOT_FOUND");
        }

        UserEntity user;

        try {
            user = Option.ofOptional(userRepository.findById(userId))
                    .getOrElseThrow(()->new CustomUserNotFoundException("USER_NOT_FOUND"));
        } catch (UserException e) {
            return Error.notFound("USER_NOT_FOUND");
        }


        post.addLike(userId);
        post = postRepository.save(post);

        return Success.ok(mapPostsToListPostDetailsDto(post.getTrip().getPosts()));
    }

    public Response handDownByTripAndPostId(Long postId, Long userId) {

        Post post;

        try {
            post = Option.ofOptional(postRepository.findById(postId))
                    .getOrElseThrow(()->new PostNotFoundException("POST_NOT_FOUND"));
        } catch (TripException e) {
            return Error.notFound("POST_NOT_FOUND");
        }

        post.addDislike(userId);
        post = postRepository.save(post);

        return Success.ok(mapPostsToListPostDetailsDto(post.getTrip().posts));
    }

    public Response addNewDateProposition(DatePropositionDto datePropositionDto, Long tripId) {

        Trip trip;
        try {
            trip = Option.ofOptional(tripRepository.findById(tripId))
                    .getOrElseThrow(() -> new CustomUserNotFoundException("USER_NOT_FOUND"));
        } catch (UserException e) {
            return Error.notFound("USER_NOT_FOUND");
        }

        if (trip.findNumberOfAddPropositions(datePropositionDto.getOwnerId()) > 3 ) {
            return Error.badRequest("DATE_PROPOSITION_LIMIT_REACHED");
        }

        DateProposition proposition = modelMapper.map(datePropositionDto, DateProposition.class);
        datePropositionRepository.save(proposition);
        trip.addDateProposition(proposition);
        trip = trip.runAnalysis();

        DatePropositionReturnedListDto report = trip.getDateMatcherReport();

        tripRepository.save(trip);

        return Success.ok(report);

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
        List<PostDetailsDto> list = new ArrayList<>();

        List<Post> sortedPosts = posts.stream()
                .sorted(Comparator.comparing(Post::getTimeStamp).reversed())
                .collect(Collectors.toList());

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

    private boolean checkIfAllUserExists(List<ExpenseRecordCreateDto> records){
        for (ExpenseRecordCreateDto it : records) {
            if (!userRepository.existsById(it.getUserId())){
                return true;
            }
        }
        return false;
    }

    private boolean checkIfAllUserExistsBeforeUpdate(List<ExpenseRecordsUpdateDto> records){
        for (ExpenseRecordsUpdateDto it : records) {
            if (!userRepository.existsById(it.getUserId())){
                return true;
            }
        }
        return false;
    }

    private Expense mapExpensesCreateDto(ExpensesCreateDto newExpenses){

        List<ExpenseRecord> records = new ArrayList<>();
        Optional<UserEntity> optional;

        Expense expense = new Expense(
                newExpenses.getTitle(),
                newExpenses.getDescription(),
                newExpenses.getCost(),
                records
        );

        expense = expensesRepository.save(expense);

        for (ExpenseRecordCreateDto it : newExpenses.getShareHolders()) {

            optional = userRepository.findById(it.getUserId());

            if(optional.isEmpty()){
                return null;
            }

            ExpenseRecord record = new ExpenseRecord(optional.get(), it.getAmount());
            record = expenseRecordRepository.save(record);
            records.add(record);
            expenseRecordRepository.save(record);
        }

        expense.setShareholders(records);
        expense = expensesRepository.save(expense);
        return expense;
    }

}
