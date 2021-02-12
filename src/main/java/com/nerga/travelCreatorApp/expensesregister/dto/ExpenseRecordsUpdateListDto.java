package com.nerga.travelCreatorApp.expensesregister.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ExpenseRecordsUpdateListDto {
    Long tripId;
    Long expenseId;
    List<ExpenseRecordsUpdateDto> recordsToUpdate;
}
