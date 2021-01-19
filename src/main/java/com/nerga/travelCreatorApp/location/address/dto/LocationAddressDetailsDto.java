package com.nerga.travelCreatorApp.location.address.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationAddressDetailsDto {
    private Long   locationAddressId;
    private String countryName;
    private String cityName;
    private String streetNameAndNumber;
    private String zipCode;
    private double longitude;
    private double latitude;
}
