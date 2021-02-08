package com.nerga.travelCreatorApp.location;

import com.nerga.travelCreatorApp.location.address.LocationAddress;
import com.nerga.travelCreatorApp.location.dto.LocationDetailsDto;
import com.nerga.travelCreatorApp.security.auth.database.UserEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long locationId;
    private String locationName;
    private String locationDescription;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="location_address_id", referencedColumnName = "id")
    private LocationAddress locationAddress;
    @ManyToOne
    private UserEntity owner;
    private boolean isPrivate;

    public Location updateLocationEntity(LocationDetailsDto locationDetailsDto) {

        this.setLocationName(simplyValidatorInputEmptyString(
                locationDetailsDto.getLocationName(),
                this.getLocationName()));
        this.setLocationDescription(simplyValidatorInputEmptyString(
                locationDetailsDto.getLocationDescription(),
                this.getLocationDescription()));
        this.setPrivate(locationDetailsDto.isPrivate());
        this.setLocationAddress(
                locationAddress.updateLocationEntity(locationDetailsDto.getLocationAddress()));

        return this;
    }

    private String simplyValidatorInputEmptyString(String newInput, String oldInput){
        return newInput.isBlank() ? oldInput : newInput;
    }

}
