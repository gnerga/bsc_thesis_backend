package com.nerga.travelCreatorApp.location.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationAddressDetailsDto {
    private String countryName;
    private String cityName;
    private String streetNameAndNumber;
    private String zipCode;
    private double longitude;
    private double latitude;
}
