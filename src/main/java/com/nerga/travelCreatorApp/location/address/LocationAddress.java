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
    private String street;
    private int number;
    private String numberExtension;
    private String zipCode;

    public LocationAddress updateLocationEntity(LocationAddressDetailsDto locationDetailsDto) {

//        this.locationAddressId = locationDetailsDto.getLocationAddressId();
        this.countryName = simplyValidatorInputEmptyString(locationDetailsDto.getCountryName(), this.countryName);
        this.cityName = simplyValidatorInputEmptyString(locationDetailsDto.getCityName(), this.getCityName());
        this.street = simplyValidatorInputEmptyString(locationDetailsDto.getStreet(), this.getStreet());
        this.number = locationDetailsDto.getNumber();
        this.numberExtension = simplyValidatorInputEmptyString(locationDetailsDto.getNumberExtension(), this.numberExtension);
        this.zipCode = simplyValidatorInputEmptyString(locationDetailsDto.getZipCode(), this.zipCode);
        return this;
    }

    private String simplyValidatorInputEmptyString(String newInput, String oldInput){
        return newInput.isBlank() ? oldInput : newInput;
    }

}
