package com.nerga.travelCreatorApp.trip.dto;

import com.nerga.travelCreatorApp.datepropositionmatcher.dto.DatePropositionReturnedListDto;
import com.nerga.travelCreatorApp.expensesregister.dto.ExpensesDetailsDto;
import com.nerga.travelCreatorApp.location.dto.LocationDetailsDto;
import com.nerga.travelCreatorApp.post.dto.PostDetailsDto;
import com.nerga.travelCreatorApp.security.dto.UserDetailsDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TripDetailsDto {

    Long tripId;
    String tripName;
    String tripDescription;
    LocalDate startDate;
    LocalDate endDate;
    LocationDetailsDto locationDetailsDto;
    DatePropositionReturnedListDto dateProposition;
    List<PostDetailsDto> posts;
    List<ExpensesDetailsDto> expenses;
    List<UserDetailsDto> organizers;
    List<UserDetailsDto> participants;

}
