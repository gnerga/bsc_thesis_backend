package com.nerga.travelCreatorApp.trip.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripCreateDto {

    private String tripName;
    private String tripDescription;
    private String startDate;
    private String endDate;

    private Long locationId;
    private Long creatorId;


}
