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
import com.nerga.travelCreatorApp.expensesregister.Expenses;
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

       Expenses expense = mapExpensesCreateDto(newExpenses);

       trip.addExpense(expense);

       trip = tripRepository.save(trip);

//       return Success.ok(mapExpensesToExpensesDetailsDto(expense));
       return Success.ok(mapExpensesListToExpensesDetailsDtoLost(trip.getExpenses()));
    }

    public Response updateExpense(ExpenseUpdateDto expenseUpdateDto) {

        Expenses expense;

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
        Expenses expense;

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

        return Success.ok(Success.accepted(mapExpensesToExpensesDetailsDto(expense)));
    }

    public Response updateExpenseShareholdersAmount(ExpenseRecordsUpdateListDto updatedRecordList) {

        Expenses expense;

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

        return Success.ok(mapExpensesToExpensesDetailsDto(expense));
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
        List<PostDetailsDto> list = new ArrayList<>();

//        Collections.sort(posts);
//        Collections.reverse(posts);
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

    private Expenses mapExpensesCreateDto(ExpensesCreateDto newExpenses){

        List<ExpenseRecord> records = new ArrayList<>();
        Optional<UserEntity> optional;

        Expenses expenses = new Expenses(
                newExpenses.getTitle(),
                newExpenses.getDescription(),
                newExpenses.getCost(),
                records
        );

        expenses = expensesRepository.save(expenses);

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

        expenses.setShareholders(records);
        expenses = expensesRepository.save(expenses);
        return expenses;
    }

}
