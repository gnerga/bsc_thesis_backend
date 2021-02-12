package com.nerga.travelCreatorApp.location;

import com.nerga.travelCreatorApp.common.propertymap.ApplicationPropertyMaps;
import com.nerga.travelCreatorApp.location.address.LocationAddress;
import com.nerga.travelCreatorApp.location.address.dto.LocationAddressCreateDto;
import com.nerga.travelCreatorApp.location.address.dto.LocationAddressDetailsDto;
import com.nerga.travelCreatorApp.location.dto.LocationCreateDto;
import com.nerga.travelCreatorApp.location.dto.LocationDetailsDto;

import com.nerga.travelCreatorApp.security.auth.database.UserEntity;
import com.nerga.travelCreatorApp.security.dto.UserDetailsDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class LocationTest {

    private ModelMapper modelMapper;

    @BeforeEach
    public void beforeTest(){
        modelMapper = new ModelMapper();
        modelMapper.addMappings(ApplicationPropertyMaps.userEntityFieldMapping());
    }

    @Test
    public void whenConvertLocationToLocationDetailsDto_thenCorrect(){

        UserEntity userEntity = getTestUserEntity();

        LocationAddress locationAddress = getTestLocationAddress();

        Location location = getTestLocation();

        LocationDetailsDto locationDetailsDto = modelMapper.map(location, LocationDetailsDto.class);
        UserDetailsDto userDetailsDto = modelMapper.map(userEntity, UserDetailsDto.class);

        assertEquals(location.getLocationName(), locationDetailsDto.getLocationName());
        assertEquals(location.getLocationDescription(), locationDetailsDto.getLocationDescription());
        assertEquals(location.getLocationAddress().getLocationAddressId(), locationDetailsDto.getLocationAddress().getLocationAddressId());
        assertEquals(location.getLocationAddress().getCountryName(), locationDetailsDto.getLocationAddress().getCountryName());
        assertEquals(location.getLocationId(), locationDetailsDto.getLocationId());
        assertEquals(userDetailsDto, locationDetailsDto.getOwner());

    }

    @Test
    public void whenConvertLocationCreateDtoToLocationEntity_thenCorrect(){

        LocationAddressCreateDto locationAddressCreateDto = getTestLocationAddressCreateDto();

        LocationCreateDto locationCreateDto = new LocationCreateDto();
        locationCreateDto.setLocationName("Super Spot");
        locationCreateDto.setLocationDescription("Uber opis ziomek");
        locationCreateDto.setLocationAddress(locationAddressCreateDto);


        Location location = modelMapper.map(locationCreateDto, Location.class);
        assertEquals(locationCreateDto.getLocationName(), location.getLocationName());
        assertEquals(locationCreateDto.getLocationDescription(), location.getLocationDescription());
        assertEquals(locationCreateDto.getLocationAddress().getCityName(), location.getLocationAddress().getCityName());

    }

    @Test
    void shouldUpdateLocationEntity() {
        Location location = getTestLocation();
        LocationDetailsDto locationDetailsDto = getLocationDetailsDto();
        Location updatedLocation = location.updateLocationEntity(locationDetailsDto);

        assertEquals(updatedLocation.getLocationName(), locationDetailsDto.getLocationName());

    }

    private UserEntity getTestUserEntity(){
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setUsername("test_user");
        userEntity.setPassword("password_1");
        userEntity.setFirstName("Jan");
        userEntity.setLastName("Nowak");
        userEntity.setEmail("test@mail.com");
        userEntity.setPhoneNumber("1234516");
        return userEntity;
    }

    private Location getTestLocation(){
        Location location = new Location();
        location.setLocationName("Super Spot");
        location.setLocationAddress(getTestLocationAddress());
        location.setOwner(getTestUserEntity());
        location.setLocationDescription("Super miejscówa, ziom");
        location.setLocationId(1L);
        location.setPrivate(true);
        return location;
    }

    private LocationAddressCreateDto getTestLocationAddressCreateDto(){
        return new LocationAddressCreateDto(
                "Poland",
                "Lodz",
                "Tunelowa 1",
                "90-156",
                40.40,
                30.40
        );
    }

    private LocationAddress getTestLocationAddress(){
        return  new LocationAddress(
                1L,
                "Poland",
                "Lodz",
                "Tunelowa 1",
                "90-156",
                40.40,
                30.40
        );
    }

    private LocationDetailsDto getLocationDetailsDto(){
        LocationDetailsDto location = new LocationDetailsDto();
        location.setLocationName("Mazury 2k21");
        location.setLocationAddress(modelMapper.map(getTestLocationAddress(), LocationAddressDetailsDto.class));
        location.setOwner(modelMapper.map(getTestUserEntity(), UserDetailsDto.class));
        location.setLocationDescription("Super miejscówa, ziom");
        location.setLocationId(1L);
        location.setPrivate(false);
        return location;
    }



}
