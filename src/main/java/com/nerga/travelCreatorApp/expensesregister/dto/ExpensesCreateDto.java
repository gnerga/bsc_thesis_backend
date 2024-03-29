package com.nerga.travelCreatorApp.expensesregister.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpensesCreateDto {

    Long tripId;
    String title;
    String description;
    float cost;
    List<ExpenseRecordCreateDto> shareHolders;

}
