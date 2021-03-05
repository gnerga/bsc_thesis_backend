package com.nerga.travelCreatorApp.trip.dto;

import com.nerga.travelCreatorApp.expensesregister.dto.ExpensesSummaryDto;
import com.nerga.travelCreatorApp.security.dto.UserSummaryDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TripSummaryDto {

    String tripName;
    String tripDescription;

    String startDate;
    String endDate;

    String country;
    String address;

    String locationDescription;

    double totalExpenses;

    List<UserSummaryDto> participants;
    List<ExpensesSummaryDto> expensesSummary;

}
