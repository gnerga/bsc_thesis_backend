package com.nerga.travelCreatorApp.location.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationAddressCreateDto {
    private String countryName;
    private String cityName;
    private String street;
    private int number;
    private String numberExtension;
    private String zipCode;
}
