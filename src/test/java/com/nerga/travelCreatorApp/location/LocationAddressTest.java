package com.nerga.travelCreatorApp.location;

import com.nerga.travelCreatorApp.location.dto.LocationAddressCreateDto;
import com.nerga.travelCreatorApp.security.auth.database.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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
                "Tunelowa",
                1,
                "m. 1",
                "90-156"
        );
        assertEquals(1L, locationAddress.getLocationAddressId());
        assertEquals("Poland", locationAddress.getCountryName());
        assertEquals("Lodz", locationAddress.getCityName());
        assertEquals("Tunelowa", locationAddress.getStreet());
        assertEquals(1, locationAddress.getNumber());
        assertEquals("m. 1", locationAddress.getNumberExtension());
        assertEquals("90-156", locationAddress.getZipCode());
    }

    @Test
    void shouldConvertLocationAddressDtoToLocationAddressEntity(){
        LocationAddressCreateDto locationAddressCreateDto =
                new LocationAddressCreateDto(
                        "Poland",
                        "Lodz",
                        "Tunelowa",
                        1,
                        "",
                        "90-156"
                );

        LocationAddress locationAddress = modelMapper.map(locationAddressCreateDto, LocationAddress.class);

        assertEquals(locationAddressCreateDto.getCityName(), locationAddress.getCityName());
    }

}
