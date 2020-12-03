package com.nerga.travelCreatorApp;

import com.nerga.travelCreatorApp.location.Location;
import com.nerga.travelCreatorApp.security.auth.User;
import com.nerga.travelCreatorApp.security.auth.database.UserEntity;
import com.nerga.travelCreatorApp.trip.Trip;
import com.nerga.travelCreatorApp.trip.dto.TripCreateDto;
import com.nerga.travelCreatorApp.trip.dto.TripDetailsDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.*;
import org.modelmapper.convention.MatchingStrategies;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TripTest {

    ModelMapper modelMapper;
    UserEntity userEntity;
    UserEntity userEntity_2;
    Location location;
    Trip trip_2;

    @Before
    public void beforeTest(){

        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        Provider<LocalDate> localDateProvider = new AbstractProvider<LocalDate>() {
            @Override
            public LocalDate get() {
                return LocalDate.now();
            }
        };

        Converter<String, LocalDate> toStringDate = new AbstractConverter<String, LocalDate>() {
            @Override
            protected LocalDate convert(String source) {
                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                return LocalDate.parse(source, format);
            }
        };


        modelMapper.createTypeMap(String.class, LocalDate.class);
        modelMapper.addConverter(toStringDate);
        modelMapper.getTypeMap(String.class, LocalDate.class).setProvider(localDateProvider);

        userEntity = new UserEntity();
        userEntity_2 = new UserEntity();

        userEntity.setId(1L);
        userEntity.setUsername("test_user");
        userEntity.setPassword("password_1");
        userEntity.setFirstName("Jan");
        userEntity.setLastName("Nowak");
        userEntity.setEmail("test@mail.com");
        userEntity.setPhoneNumber("1234516");

        userEntity_2.setId(2L);
        userEntity_2.setUsername("test_user_2");
        userEntity_2.setPassword("password_1");
        userEntity_2.setFirstName("Adam");
        userEntity_2.setLastName("Nowak");
        userEntity_2.setEmail("test2@mail.com");
        userEntity_2.setPhoneNumber("2234516");

        location = new Location();
        location.setLocationName("Super Spot");
        location.setGoogleMapUrl("htttp/222/222");
        location.setLocationDescription("Super miejscówa, ziom");
        location.setLocationId(1L);

        trip_2 = new Trip();

        trip_2.setTripName("Wakacje Ekipy");
        trip_2.setTripDescription("Najlepsze wakacje ever");
        trip_2.setStartDate(LocalDate.parse("2020-11-24"));
        trip_2.setEndDate(LocalDate.parse("2020-12-04"));
        trip_2.setLocation(location);
        trip_2.addOrganizer(userEntity);

    }

    @Test
    public void whenCreateNewTrip() {

        Trip trip = new Trip();
        trip.setTripName("Wakacje Ekipy");
        trip.setTripDescription("Najlepsze wakacje ever");
        trip.setStartDate(LocalDate.parse("2020-11-24"));
        trip.setEndDate(LocalDate.parse("2020-12-04"));
        trip.setLocation(location);
        trip.addOrganizer(userEntity);

        Assert.assertEquals(userEntity, trip.getOrganizers().get(0));
        Assert.assertEquals(location, trip.getLocation());

        Assert.assertEquals("Wakacje Ekipy", trip.getTripName());
        Assert.assertEquals("Najlepsze wakacje ever", trip.getTripDescription());
        Assert.assertEquals(LocalDate.parse("2020-11-24"), trip.getStartDate());
        Assert.assertEquals(LocalDate.parse("2020-12-04"), trip.getEndDate());
        Assert.assertEquals(trip, userEntity.getOrganizedTrips().get(0));

    }

    @Test
    public void addNewParticipant(){
        trip_2.addParticipant(userEntity_2);
        Assert.assertEquals(userEntity_2, trip_2.getParticipants().get(0));
        Assert.assertEquals(trip_2, userEntity_2.getParticipatedTrips().get(0));
    }

    @Test
    public void removeParticipant(){
        UserEntity userEntity_3 = new UserEntity();
        userEntity_3.setId(3L);
        userEntity_3.setUsername("test_user_3");
        userEntity_3.setPassword("password_1");
        userEntity_3.setFirstName("Wiktor");
        userEntity_3.setLastName("Nowak");
        userEntity_3.setEmail("test3@mail.com");
        userEntity_3.setPhoneNumber("3234516");

        trip_2.addParticipant(userEntity_2);
        trip_2.addParticipant(userEntity_3);

        Assert.assertEquals(userEntity_3, trip_2.getParticipants().get(1));

        trip_2.removeParticipant(userEntity_2);
        trip_2.removeParticipant(userEntity_3);

        Assert.assertTrue(trip_2.getParticipants().isEmpty());

    }

    @Test
    public void whenConvertTripEntityWithUserToTripDetailsDto_thenCorrect() {

        trip_2.addParticipant(userEntity_2);
        TripDetailsDto tripDetailsDto = modelMapper.map(trip_2, TripDetailsDto.class);
        Assert.assertEquals(trip_2.getTripName(), tripDetailsDto.getTripName());
        Assert.assertEquals(trip_2.getOrganizers().get(0).getUsername(), tripDetailsDto.getOrganizers().get(0).getUsername());
        Assert.assertEquals(trip_2.getLocation().getLocationName(), tripDetailsDto.getLocation().getLocationName());

    }

    @Test
    public void whenConvertTripCreateDtoToTripEntity_thenCorrect(){

        TripCreateDto tripCreateDto = new TripCreateDto();
        tripCreateDto.setTripName("Wakacje");
        tripCreateDto.setTripDescription("Wycieczka");
        tripCreateDto.setStartDate("2020-11-24");
        tripCreateDto.setEndDate("2020-10-24");
        tripCreateDto.setCreatorId(1L);
        tripCreateDto.setLocationId(1L);

        Trip trip = modelMapper.map(tripCreateDto, Trip.class);

        Assert.assertEquals(tripCreateDto.getTripName(), trip.getTripName());
        Assert.assertEquals(tripCreateDto.getTripDescription(), trip.getTripDescription());
        Assert.assertEquals(LocalDate.parse(tripCreateDto.getStartDate()), trip.getStartDate());
        Assert.assertEquals(LocalDate.parse(tripCreateDto.getEndDate()), trip.getEndDate());


    }

}
