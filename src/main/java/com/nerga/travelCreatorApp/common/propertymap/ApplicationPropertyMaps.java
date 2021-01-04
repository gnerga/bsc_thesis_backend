package com.nerga.travelCreatorApp.common.propertymap;

import com.nerga.travelCreatorApp.location.Location;
import com.nerga.travelCreatorApp.location.dto.LocationDetailsDto;
import org.modelmapper.PropertyMap;

public class ApplicationPropertyMaps {

    public static PropertyMap<Location, LocationDetailsDto> userEntityFieldMapping() {
        return new PropertyMap<Location, LocationDetailsDto>() {
            @Override
            protected void configure() {
                map().getOwner().setId(source.getOwnerEntity().getId());
                map().getOwner().setUsername(source.getOwnerEntity().getUsername());
                map().getOwner().setFirstName(source.getOwnerEntity().getFirstName());
                map().getOwner().setLastName(source.getOwnerEntity().getLastName());
                map().getOwner().setEmail(source.getOwnerEntity().getEmail());
                map().getOwner().setPhoneNumber(source.getOwnerEntity().getPhoneNumber());
            }
        };
    }

}
