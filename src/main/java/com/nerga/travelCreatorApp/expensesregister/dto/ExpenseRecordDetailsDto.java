package com.nerga.travelCreatorApp.expensesregister.dto;

import com.nerga.travelCreatorApp.security.dto.UserDetailsDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ExpenseRecordDetailsDto {

    Long expenseRecordId;
    UserDetailsDto user;
    float amount;

}
