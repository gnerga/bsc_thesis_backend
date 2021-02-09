package com.nerga.travelCreatorApp.trip.dto;

import com.nerga.travelCreatorApp.location.dto.LocationDetailsDto;
import com.nerga.travelCreatorApp.location.dto.LocationUpdateDto;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class TripUpdateDto {

    @NotNull
    private Long tripId;
    @NotNull
    private String tripName;
    @NotNull
    private String tripDescription;
    @NotNull
    private String startDate;
    @NotNull
    private String endDate;
    @NotNull
    private LocationDetailsDto location;


}
