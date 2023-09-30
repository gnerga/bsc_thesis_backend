package com.nerga.travelCreatorApp.location;

import com.nerga.travelCreatorApp.location.address.LocationAddress;
import com.nerga.travelCreatorApp.location.dto.LocationDetailsDto;
import com.nerga.travelCreatorApp.security.auth.database.UserEntity;
import com.sun.istack.NotNull;
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

    @NotNull
    private String locationName;

    @NonNull
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
                locationAddress.updateAddressEntity(locationDetailsDto.getLocationAddress()));

        return this;
    }

    private String simplyValidatorInputEmptyString(String newInput, String oldInput){
        return newInput.isBlank() ? oldInput : newInput;
    }

    @Override
    public int hashCode() {
        return locationId == null ? 0 : locationId.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (locationId == null || obj == null || getClass() != obj.getClass())
            return false;

        Location that = (Location) obj;
        return locationId.equals(that.locationId);
    }
}
