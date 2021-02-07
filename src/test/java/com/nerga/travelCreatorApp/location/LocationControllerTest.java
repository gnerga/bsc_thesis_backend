package com.nerga.travelCreatorApp.location;

import com.nerga.travelCreatorApp.location.Location;
import com.nerga.travelCreatorApp.location.LocationController;
import com.nerga.travelCreatorApp.location.LocationService;
import com.nerga.travelCreatorApp.location.dto.LocationCreateDto;


public class LocationControllerTest {

    public void testAddLocation() throws Exception {

        String uri = "/location/createLocation";

        LocationCreateDto locationCreateDto = new LocationCreateDto();
        locationCreateDto.setLocationName("Test");
        locationCreateDto.setLocationDescription("Opis");


        Location location = new Location();
        location.setLocationId(1L);
        location.setLocationName("Test");
        location.setLocationDescription("Opis");


    }



}
