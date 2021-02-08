package com.nerga.travelCreatorApp.trip.dto;

import com.nerga.travelCreatorApp.location.dto.LocationDetailsDto;
import com.nerga.travelCreatorApp.security.dto.UserDetailsDto;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class TripDetailsDto {

    @NotNull
    private Long tripId;
    @NotNull
    private String tripName;
    @NotNull
    private String tripDescription;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    @NotNull
    private LocationDetailsDto location;


    private List<UserDetailsDto> organizers;
    private List<UserDetailsDto> participants;

}
