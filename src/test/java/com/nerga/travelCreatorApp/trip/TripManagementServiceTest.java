package com.nerga.travelCreatorApp.trip;

import com.nerga.travelCreatorApp.common.propertymap.ApplicationPropertyMaps;
import com.nerga.travelCreatorApp.common.response.Response;
import com.nerga.travelCreatorApp.datepropositionmatcher.DateProposition;
import com.nerga.travelCreatorApp.location.Location;
import com.nerga.travelCreatorApp.location.LocationRepository;
import com.nerga.travelCreatorApp.location.address.LocationAddress;
import com.nerga.travelCreatorApp.location.address.dto.LocationAddressDetailsDto;
import com.nerga.travelCreatorApp.location.dto.LocationDetailsDto;
import com.nerga.travelCreatorApp.security.auth.database.UserEntity;
import com.nerga.travelCreatorApp.security.auth.database.UserRepository;
import com.nerga.travelCreatorApp.security.dto.UserDetailsDto;
import com.nerga.travelCreatorApp.trip.dto.TripCreateDto;
import com.nerga.travelCreatorApp.trip.dto.TripDetailsDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.*;

import static org.mockito.BDDMockito.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class TripManagementServiceTest {

    @Mock
    private LocationRepository locationRepository;
    @Mock
    private TripRepository tripRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ModelMapper modelMapper;

    @Mock
    Authentication authentication;
    @Mock
    SecurityContext securityContext;

    @Captor
    private ArgumentCaptor<Trip> tripArgumentCaptor;


    private TripManagementService underTest;
    private UserEntity userEntity;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        modelMapper.addMappings(ApplicationPropertyMaps.userEntityFieldMapping());
        underTest = new TripManagementService(
                tripRepository,
                locationRepository,
                userRepository,
                modelMapper
        );
        userEntity = getTestUserEntity();
        Authentication auth = new UsernamePasswordAuthenticationToken(userEntity,null);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Test
    public void itShouldSaveNewTrip(){

        //Given
        TripCreateDto tripCreateDto = getTripCreateDto();

        Location location = getTestLocation();
        Trip testTrip = getTestTrip();
        TripDetailsDto tripTestDetailsDto = getTestTripDetailsDto();
        String loggedUser = "test";
        Map<String, Object> details = new HashMap<String, Object>();
        details.put("user_name", "test");

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(userEntity));
        when(locationRepository.findById(1L)).thenReturn(Optional.of(location));
        when(modelMapper.map(tripCreateDto, Trip.class)).thenReturn(testTrip);
        // When
        underTest.addTrip(tripCreateDto);
        // Then
        then(tripRepository)
                .should()
                .save(tripArgumentCaptor.capture());
        Trip tripArgumentCaptorValue = tripArgumentCaptor.getValue();
        Assertions.assertThat(tripArgumentCaptorValue).isEqualTo(testTrip);

    }

    @Test
    public void itShouldRequestCode200(){
        //Given
        TripCreateDto tripCreateDto = getTripCreateDto();

        Location location = getTestLocation();
        Trip testTrip = getTestTrip();
        TripDetailsDto tripTestDetailsDto = getTestTripDetailsDto();
        String loggedUser = "test";
        Map<String, Object> details = new HashMap<String, Object>();
        details.put("user_name", "test");

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(userEntity));
        when(locationRepository.findById(1L)).thenReturn(Optional.of(location));
        when(modelMapper.map(tripCreateDto, Trip.class)).thenReturn(testTrip);
        when(tripRepository.save(testTrip)).thenReturn(testTrip);
        when(modelMapper.map(testTrip, TripDetailsDto.class)).thenReturn(tripTestDetailsDto);
        // When
        Response response = underTest.addTrip(tripCreateDto);
        // Then
        Assertions.assertThat(
                response.toResponseEntity()
                .getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(
                response.toResponseEntity()
                        .getBody())
                .isEqualToComparingFieldByField(tripTestDetailsDto);
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

    private TripCreateDto getTripCreateDto(){
        return new TripCreateDto(
                "Holidays 2020",
                "Friends meet after years",
                true,
                7,
                "2021-05-12",
                "2021-05-19",
                1L
        );
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

    private LocationAddressDetailsDto getTestAddressDetailsDto(){
        return new LocationAddressDetailsDto(
                1L,
                "Poland",
                "Lodz",
                "Tunelowa 1",
                "90-156",
                40.40,
                30.40
        );
    }

    private Trip getTestTrip(){

        return new Trip(
                1L,
                "Holidays 2020",
                getTestLocation(),
                "Friends meet after years",
                true,
                7,
                LocalDate.parse("2021-05-12"),
                LocalDate.parse("2021-05-19"),
                LocalDate.parse("2021-05-19")
        );
    }

    private LocationDetailsDto getTestLocationDetailsDto(){
        return new LocationDetailsDto(
                1L,
                "Super Spot",
                "Super miejscówa, ziom",
                getTestAddressDetailsDto(),
                getTestUserDetailsDto(),
                true
        );
    }

    private TripDetailsDto getTestTripDetailsDto(){
        return new TripDetailsDto(
                1L,
                "Holidays 2020",
                "Friends meet after years",
                LocalDate.parse("2021-05-12"),
                LocalDate.parse("2021-05-19"),
                getTestLocationDetailsDto(),
                getTestOrganizersList(),
                getTestParticipantsList()
        );
    }

    private List<UserDetailsDto> getTestOrganizersList(){
        List<UserDetailsDto> list = new ArrayList<>();
        list.add(getTestUserDetailsDto());
        return list;
    }

    private List<UserDetailsDto> getTestParticipantsList(){
        List<UserDetailsDto> list = new ArrayList<>();
        return list;
    }

    private DateProposition getTestDateProposition(){
        return new
                DateProposition(LocalDate.parse("2020-10-14"),
                LocalDate.parse("2020-10-21"), "test_user", 1L);
    }

    private UserDetailsDto getTestUserDetailsDto(){
        return new UserDetailsDto(
                1L,
                "test_user",
                "Jan",
                "Nowak",
                "test@gmail.com",
                "111222333"

        );
    }

}
