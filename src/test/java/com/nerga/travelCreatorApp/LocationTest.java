package com.nerga.travelCreatorApp;

import com.nerga.travelCreatorApp.location.Location;
import com.nerga.travelCreatorApp.location.dto.LocationCreateDto;
import com.nerga.travelCreatorApp.location.dto.LocationDetailsDto;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;

import static org.junit.Assert.assertEquals;

public class LocationTest {

    private ModelMapper modelMapper;

    @Before
    public void beforeTest(){
        modelMapper = new ModelMapper();
    }

    @Test
    public void whenConvertLocationToLocationDetailsDto_thenCorrect(){
        Location location = new Location();
        location.setLocationName("Super Spot");
        location.setGoogleMapUrl("htttp/222/222");
        location.setLocationDescription("Super miejscówa, ziom");
        location.setLocationId(1L);

        LocationDetailsDto locationDetailsDto = modelMapper.map(location, LocationDetailsDto.class);

        assertEquals(location.getGoogleMapUrl(), locationDetailsDto.getGoogleMapUrl());
        assertEquals(location.getLocationName(), locationDetailsDto.getLocationName());
        assertEquals(location.getLocationDescription(), locationDetailsDto.getLocationDescription());
        assertEquals(location.getLocationId(), locationDetailsDto.getId());

    }

    @Test
    public void whenConvertLocationCreateToLocationEntity_thenCorrect(){

        LocationCreateDto locationCreateDto = new LocationCreateDto();
        locationCreateDto.setLocationName("Super Spot");
        locationCreateDto.setLocationDescription("Uber opis ziomek");
        locationCreateDto.setGoogleMapUrl("url/url");

        Location location = modelMapper.map(locationCreateDto, Location.class);

        assertEquals(locationCreateDto.getLocationName(), location.getLocationName());
        assertEquals(locationCreateDto.getLocationDescription(), location.getLocationDescription());
        assertEquals(locationCreateDto.getGoogleMapUrl(), location.getGoogleMapUrl());

    }


}