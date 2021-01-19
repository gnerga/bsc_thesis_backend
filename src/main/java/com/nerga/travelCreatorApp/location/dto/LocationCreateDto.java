package com.nerga.travelCreatorApp.location.dto;

import com.nerga.travelCreatorApp.location.address.dto.LocationAddressCreateDto;
import com.nerga.travelCreatorApp.security.dto.UserDetailsDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LocationCreateDto {

    private String locationName;
    private String locationDescription;
    private LocationAddressCreateDto locationAddress;
    private UserDetailsDto owner;
    private String googleMapUrl;

}
