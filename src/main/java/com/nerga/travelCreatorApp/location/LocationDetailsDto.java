package com.nerga.travelCreatorApp.location;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LocationDetailsDto {

    private Long id;
    private String locationName;
    private String locationDescription;
    private String googleMapUrl;

}
