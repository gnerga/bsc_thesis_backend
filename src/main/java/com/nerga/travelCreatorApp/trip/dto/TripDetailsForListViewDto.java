package com.nerga.travelCreatorApp.trip.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TripDetailsForListViewDto {

    Long tripId;
    String tripName;
    String locationName;

}
