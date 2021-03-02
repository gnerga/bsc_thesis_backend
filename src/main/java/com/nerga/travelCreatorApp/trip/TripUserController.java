package com.nerga.travelCreatorApp.trip;

import com.nerga.travelCreatorApp.datepropositionmatcher.dto.DatePropositionDto;
import com.nerga.travelCreatorApp.expensesregister.dto.ExpenseRecordCreateDto;
import com.nerga.travelCreatorApp.expensesregister.dto.ExpenseRecordsUpdateListDto;
import com.nerga.travelCreatorApp.expensesregister.dto.ExpenseUpdateDto;
import com.nerga.travelCreatorApp.expensesregister.dto.ExpensesCreateDto;
import com.nerga.travelCreatorApp.post.dto.PostCreateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trip/user")

public class TripUserController {

    private final TripUserService tripUserService;

    @Autowired
    public TripUserController(TripUserService tripUserService) {
        this.tripUserService = tripUserService;
    }

    @PostMapping("/expense")
    public ResponseEntity addExpense(@RequestBody ExpensesCreateDto newExpenses){
        return tripUserService.createExpense(newExpenses).toResponseEntity();
    }

    @PutMapping("/expense")
    public ResponseEntity updateExpense(@RequestBody ExpenseUpdateDto expenseUpdateDto){
        return tripUserService.updateExpense(expenseUpdateDto).toResponseEntity();
    }

    @PutMapping("/addExpenseRecord")
    public ResponseEntity addExpenseRecord(@RequestBody ExpenseRecordCreateDto newRecord){
        return tripUserService.addUserToExpense(newRecord).toResponseEntity();
    }

    @PutMapping("/updateExpenseShareholdersList")
    public ResponseEntity updateExpenseShareHoldersList(@RequestBody ExpenseRecordsUpdateListDto recordUpdateList){
        return tripUserService.updateExpenseShareholders(recordUpdateList).toResponseEntity();
    }

    @PostMapping("/addPostToTrip={tripId}")
    public ResponseEntity addPostToTrip(@RequestBody PostCreateDto post, @PathVariable("tripId") Long tripId){
        return tripUserService.addPostByTripId(post, tripId).toResponseEntity();
    }

    @PutMapping("/giveHandUpToPost={postId}&{userId}")
    public ResponseEntity handUp(@PathVariable("postId") Long postId, @PathVariable("userId") Long userId){
        return tripUserService.handUpByTripAndPostId(postId, userId).toResponseEntity();
    }

    @PutMapping("/giveHandDownToPost={postId}&{userId}")
    public ResponseEntity downUp(@PathVariable("postId") Long postId, @PathVariable("userId") Long userId){
        return tripUserService.handDownByTripAndPostId(postId, userId).toResponseEntity();
    }

    @PostMapping("/addDatePropositionToTrip={tripId}")
    public ResponseEntity addDateProposition(@RequestBody DatePropositionDto datePropositionDto, @PathVariable("tripId") Long tripId){
        return tripUserService.addNewDateProposition(datePropositionDto, tripId).toResponseEntity();
    }

}