package com.nerga.travelCreatorApp.trip;

import com.nerga.travelCreatorApp.expensesregister.dto.ExpenseRecordCreateDto;
import com.nerga.travelCreatorApp.expensesregister.dto.ExpenseUpdateDto;
import com.nerga.travelCreatorApp.expensesregister.dto.ExpensesCreateDto;
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

    @PostMapping("/addExpenseRecord")
    public ResponseEntity addExpenseRecord(@RequestBody ExpenseRecordCreateDto newRecord){
        return tripUserService.addUserToExpense(newRecord).toResponseEntity();
    }

}