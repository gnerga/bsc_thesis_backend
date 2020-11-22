package com.nerga.travelCreatorApp.trip.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TripCreateDto {

    private String tripName;
    private String tripDescription;
    private Long locationId;
    private String startDate;
    private String endDate;
    private Long creatorId;

}
