package com.nerga.travelCreatorApp.location.address;

import com.nerga.travelCreatorApp.location.address.dto.LocationAddressDetailsDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="location_address")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long locationAddressId;
    private String countryName;
    private String cityName;
    private String streetNameAndNumber;
    private String zipCode;
    private double latitude;
    private double longitude;

    public LocationAddress updateLocationEntity(LocationAddressDetailsDto locationDetailsDto) {

//        this.locationAddressId = locationDetailsDto.getLocationAddressId();
        this.countryName = simplyValidatorInputEmptyString(locationDetailsDto.getCountryName(), this.countryName);
        this.cityName = simplyValidatorInputEmptyString(locationDetailsDto.getCityName(), this.getCityName());
        this.streetNameAndNumber = simplyValidatorInputEmptyString(locationDetailsDto.getStreetNameAndNumber(),
                this.getStreetNameAndNumber());
        this.zipCode = simplyValidatorInputEmptyString(locationDetailsDto.getZipCode(), this.zipCode);
        this.latitude = locationDetailsDto.getLatitude();
        this.longitude = locationDetailsDto.getLongitude();
        return this;
    }

    private String simplyValidatorInputEmptyString(String newInput, String oldInput){
        return newInput.isBlank() ? oldInput : newInput;
    }

}
