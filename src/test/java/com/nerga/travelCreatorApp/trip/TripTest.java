package com.nerga.travelCreatorApp.trip;

import com.nerga.travelCreatorApp.location.Location;
import com.nerga.travelCreatorApp.location.address.LocationAddress;
import com.nerga.travelCreatorApp.security.auth.database.UserEntity;
import com.nerga.travelCreatorApp.trip.Trip;
import com.nerga.travelCreatorApp.trip.dto.TripCreateDto;
import com.nerga.travelCreatorApp.trip.dto.TripDetailsDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.*;
import org.modelmapper.convention.MatchingStrategies;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TripTest {

    ModelMapper modelMapper;
    UserEntity userEntity;
    UserEntity userEntity_2;
    Location location;
    Trip trip_2;

    @BeforeEach
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

        location = getTestLocation();

        trip_2 = new Trip();
        trip_2.setTripId(1L);
        trip_2.setTripName("Wakacje Ekipy");
        trip_2.setTripDescription("Najlepsze wakacje ever");
        trip_2.setStartDate(LocalDate.parse("2020-11-24"));
        trip_2.setEndDate(LocalDate.parse("2020-12-04"));
        trip_2.setLocation(location);
        trip_2.addOrganizer(userEntity);
        trip_2.setActiveTrip(true);
        trip_2.getLocation().setPrivate(true);

    }

    @Test
    public void whenCreateNewTrip() {

        Trip trip = new Trip();
        trip.setTripName("Wakacje Ekipy");
        trip.setTripDescription("Najlepsze wakacje ever");
        trip.setStartDate(LocalDate.parse("2020-11-24"));
        trip.setEndDate(LocalDate.parse("2020-12-04"));
        trip.setLocation(location);
        trip.addOrganizer(userEntity_2);

        assertEquals(userEntity_2, trip.getOrganizers().get(0));
        assertEquals(location, trip.getLocation());
        assertEquals("Wakacje Ekipy", trip.getTripName());
        assertEquals("Najlepsze wakacje ever", trip.getTripDescription());
        assertEquals(LocalDate.parse("2020-11-24"), trip.getStartDate());
        assertEquals(LocalDate.parse("2020-12-04"), trip.getEndDate());
        assertEquals(trip, userEntity_2.getOrganizedTrips().get(0));

    }

    @Test
    public void addNewParticipant(){
        trip_2.addParticipant(userEntity_2);
        assertEquals(userEntity_2, trip_2.getParticipants().get(0));
        assertEquals(trip_2, userEntity_2.getParticipatedTrips().get(0));
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

        assertEquals(userEntity_3, trip_2.getParticipants().get(1));

        trip_2.removeParticipant(userEntity_2);
        trip_2.removeParticipant(userEntity_3);

        assertTrue(trip_2.getParticipants().isEmpty());

    }

    @Test
    public void whenConvertTripEntityWithUserToTripDetailsDto_thenCorrect() {

        trip_2.addParticipant(userEntity_2);
        TripDetailsDto tripDetailsDto = modelMapper.map(trip_2, TripDetailsDto.class);
        assertEquals(trip_2.getTripName(), tripDetailsDto.getTripName());
        assertEquals(trip_2.getTripDescription(), tripDetailsDto.getTripDescription());
        assertEquals(trip_2.getOrganizers().get(0).getUsername(), tripDetailsDto.getOrganizers().get(0).getUsername());
        assertEquals(trip_2.getLocation().getLocationName(), tripDetailsDto.getLocation().getLocationName());
        assertEquals(trip_2.getLocation().isPrivate(), tripDetailsDto.getLocation().isPrivate());


    }

    @Test
    public void whenConvertTripCreateDtoToTripEntity_thenCorrect(){

        TripCreateDto tripCreateDto = new TripCreateDto();
        tripCreateDto.setTripName("Wakacje");
        tripCreateDto.setTripDescription("Wycieczka");
        tripCreateDto.setStartDate("2020-11-24");
        tripCreateDto.setEndDate("2020-10-24");
        tripCreateDto.setLocationId(1L);

        Trip trip = modelMapper.map(tripCreateDto, Trip.class);

        assertEquals(tripCreateDto.getTripName(), trip.getTripName());
        assertEquals(tripCreateDto.getTripDescription(), trip.getTripDescription());
        assertEquals(LocalDate.parse(tripCreateDto.getStartDate()), trip.getStartDate());
        assertEquals(LocalDate.parse(tripCreateDto.getEndDate()), trip.getEndDate());


    }

    private Location getTestLocation(){
        Location location = new Location();
        location.setLocationName("Super Spot");
        location.setLocationAddress(getTestLocationAddress());
        location.setOwner(getTestUserEntity());
        location.setLocationDescription("Super miejscówa, ziom");
        location.setLocationId(1L);
        location.setPrivate(false);
        return location;
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

}
