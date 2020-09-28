package com.nerga.travelCreatorApp.dto.location;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LocationDto {

    private String locationName;
    private String locationDescription;
    private String googleMapUrl;

}
