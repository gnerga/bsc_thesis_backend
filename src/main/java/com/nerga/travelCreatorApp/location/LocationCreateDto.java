package com.nerga.travelCreatorApp.location;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LocationCreateDto {

    private String locationName;
    private String locationDescription;
    private String googleMapUrl;
    private Long votes;

}
