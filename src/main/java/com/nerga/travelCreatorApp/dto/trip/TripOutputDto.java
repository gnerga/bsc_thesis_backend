package com.nerga.travelCreatorApp.dto.trip;

import com.nerga.travelCreatorApp.model.Location;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TripOutputDto {

    private Long tripId;
    private String tripName;
    private String tripDescription;
    private String locationName;
    private String locationDescription;
    private String googleMapUrl;

}
