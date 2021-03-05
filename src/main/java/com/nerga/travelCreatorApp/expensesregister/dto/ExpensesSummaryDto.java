package com.nerga.travelCreatorApp.expensesregister.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpensesSummaryDto {

    String expenseName;
    String expenseSummary;
    String expenseCost;

}
