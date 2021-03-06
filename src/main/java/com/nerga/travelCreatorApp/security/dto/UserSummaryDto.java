package com.nerga.travelCreatorApp.security.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class UserSummaryDto {
    @NonNull
    String username;
    @NonNull
    String firstName;
    @NonNull
    String lastName;
    @NonNull
    String email;
    @NonNull
    String phoneNumber;
    double totalExpensesCost;
}
