package com.nerga.travelCreatorApp.location.dto;

import com.nerga.travelCreatorApp.location.address.dto.LocationAddressDetailsDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationUpdateDto {

    private Long locationId;
    private String locationName;
    private String locationDescription;
    private LocationAddressDetailsDto locationAddress;
    private boolean isPrivate;

}
