package com.nerga.travelCreatorApp.location.dto;

import com.nerga.travelCreatorApp.location.address.dto.LocationAddressDetailsDto;
import com.nerga.travelCreatorApp.security.dto.UserDetailsDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationDetailsDto {

    private Long locationId;
    private String locationName;
    private String locationDescription;
    private LocationAddressDetailsDto locationAddress;
    private UserDetailsDto owner;
    private boolean isPrivate;

}
