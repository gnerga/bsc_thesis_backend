package com.nerga.travelCreatorApp.location;

import com.nerga.travelCreatorApp.location.address.LocationAddress;
import com.nerga.travelCreatorApp.location.address.dto.LocationAddressCreateDto;
import com.nerga.travelCreatorApp.location.address.dto.LocationAddressDetailsDto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocationAddressTest {

    ModelMapper modelMapper;

    @BeforeEach
    void setUp(){
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @Test
    void shouldCreateLocationAddressEntity(){
        LocationAddress locationAddress = new LocationAddress(
                1L,
                "Poland",
                "Lodz",
                "Tunelowa 1",
                "90-156",
                40.40,
                30.40
        );
        assertEquals(1L, locationAddress.getLocationAddressId());
        assertEquals("Poland", locationAddress.getCountryName());
        assertEquals("Lodz", locationAddress.getCityName());
        assertEquals("90-156", locationAddress.getZipCode());
    }

    @Test
    void shouldConvertLocationAddressDtoToLocationAddressEntity(){
        LocationAddressCreateDto locationAddressCreateDto =
                new LocationAddressCreateDto(
                        "Poland",
                        "Lodz",
                        "Tunelowa 1",
                        "90-156",
                        40.40,
                        30.40
                );

        LocationAddress locationAddress = modelMapper.map(locationAddressCreateDto, LocationAddress.class);

        assertEquals(locationAddressCreateDto.getCityName(), locationAddress.getCityName());
    }

    @Test
    void shouldConvertLocationAddressToLocationDetailsDto(){
        LocationAddress locationAddress = new LocationAddress(
                1L,
                "Poland",
                "Lodz",
                "Tunelowa 1",
                "90-156",
                40.40,
                30.40
        );
        LocationAddressDetailsDto locationAddressDetailsDto = modelMapper
                .map(locationAddress, LocationAddressDetailsDto.class);

        assertEquals(locationAddress.getLocationAddressId(), locationAddressDetailsDto.getLocationAddressId());
    }

}
