package com.nerga.travelCreatorApp.location.dto;

import com.nerga.travelCreatorApp.location.address.dto.LocationAddressDetailsDto;
import com.nerga.travelCreatorApp.security.dto.UserDetailsDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class LocationDetailsDto {
    private Long locationId;
    private String locationName;
    private String locationDescription;
    private String googleMapUrl;
    private LocationAddressDetailsDto locationAddress;
    private UserDetailsDto owner;
    private Boolean isPrivate;


}
