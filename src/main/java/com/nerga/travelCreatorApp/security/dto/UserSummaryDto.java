package com.nerga.travelCreatorApp.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSummaryDto {
    String firstName;
    String lastName;
    String email;
    String phoneNumber;
    String totalExpensesCost;
}
