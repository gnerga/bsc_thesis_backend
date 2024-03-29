package com.nerga.travelCreatorApp.expensesregister.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseRecordsUpdateDto {

    Long expenseRecordId;
    Long userId;
    float amount;

}
