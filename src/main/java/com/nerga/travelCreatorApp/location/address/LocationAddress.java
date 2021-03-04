package com.nerga.travelCreatorApp.location.address;

import com.nerga.travelCreatorApp.location.address.dto.LocationAddressDetailsDto;
import com.sun.istack.NotNull;
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
    @NotNull
    private String countryName;
    @NotNull
    private String cityName;
    @NotNull
    private String streetNameAndNumber;

    private String zipCode;
    @NotNull
    private double latitude;
    @NotNull
    private double longitude;

    public LocationAddress updateAddressEntity(LocationAddressDetailsDto locationDetailsDto) {

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
