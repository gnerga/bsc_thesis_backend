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
public class ExpensesDetailsDto {

    Long expenseId;
    String title;
    String description;
    float cost;
    List<ExpensesDetailsDto> shareHolders;

}
