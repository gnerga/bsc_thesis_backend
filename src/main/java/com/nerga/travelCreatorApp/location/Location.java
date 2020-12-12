package com.nerga.travelCreatorApp.location;

import com.nerga.travelCreatorApp.location.dto.LocationDetailsDto;
import com.nerga.travelCreatorApp.security.auth.database.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long locationId;

    private String locationName;
    private String locationDescription;
    private String googleMapUrl;

//    @ManyToOne
//    private UserEntity ownerEntity;
//
//    // TODO gnerga dodać holder na zdjęcia

    public Location updateLocationEntity(LocationDetailsDto locationDetailsDto) {

        this.setLocationName(simplyValidatorInputEmptyString(
                locationDetailsDto.getLocationName(),
                this.getLocationName()));
        this.setLocationDescription(simplyValidatorInputEmptyString(
                locationDetailsDto.getLocationDescription(),
                this.getLocationDescription()));
        this.setGoogleMapUrl(simplyValidatorInputEmptyString(
                locationDetailsDto.getGoogleMapUrl(),
                this.getGoogleMapUrl()));

        return this;
    }

    private String simplyValidatorInputEmptyString(String newInput, String oldInput){
        return newInput.isBlank() ? oldInput : newInput;
    }

}
