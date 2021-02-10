package com.nerga.travelCreatorApp.expensesregister.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseUpdateDto {

    Long expenseId;
    String title;
    String description;

}
