package com.nerga.travelCreatorApp.trip;

import com.nerga.travelCreatorApp.common.propertymap.ApplicationPropertyMaps;
import com.nerga.travelCreatorApp.common.response.Error;
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

import static org.mockito.ArgumentMatchers.anyList;
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

    @Test
    public void itShouldReturnError(){
        //Given
        TripCreateDto tripCreateDto = getTripCreateDto();

        Map<String, Object> details = new HashMap<String, Object>();
        details.put("user_name", "test");

        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());

        // When
        Response response = underTest.addTrip(tripCreateDto);
        // Then
        Assertions.assertThat(
                response.toResponseEntity()
                        .getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void itShouldReturnListOfAllTripsAndCode200(){
        // Given
        Trip testTrip = getTestTrip();
        List<Trip> testTripList = new ArrayList<>();
        testTripList.add(testTrip);
        TripDetailsDto testTripDto = getTestTripDetailsDto();
        List<TripDetailsDto> testTripListDto = new ArrayList<>();
        testTripListDto.add(getTestTripDetailsDto());

        when(tripRepository.findAll()).thenReturn(testTripList);
        when(modelMapper.map(testTrip,TripDetailsDto.class)).thenReturn(testTripDto);

        // When
        Response response = underTest.findAllTrips();
        // Then
        Assertions.assertThat(
                response.toResponseEntity()
                        .getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(
                response.toResponseEntity()
                        .getBody())
                .isEqualToComparingFieldByField(testTripListDto);
    }

    @Test
    public void itShouldReturnEmptyListOfAllTrips(){
        // Given

        List<Trip> testTripList = new ArrayList<>();
        List<TripDetailsDto> testTripListDto = new ArrayList<>();


        when(tripRepository.findAll()).thenReturn(testTripList);

        // When
        Response response = underTest.findAllTrips();
        // Then
        Assertions.assertThat(
                response.toResponseEntity()
                        .getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(
                response.toResponseEntity()
                        .getBody())
                .isEqualToComparingFieldByField(testTripListDto);
    }

    @Test
    public void itShouldAddNewOrganizerById(){

        // Given
        Long userId = 2L;
        Long tripId = 1L;

        Trip trip = getTestTrip();
        UserEntity testUser = getTestUserEntity2();
        List<UserEntity> testUserList;
        List<UserDetailsDto> testUserDetailsDto = new ArrayList<>();
        UserDetailsDto userDetailsDto = getTestUserDetailsDto2();
        testUserDetailsDto.add(userDetailsDto);
        TripDetailsDto tripDto = getTestTripDetailsDtoWithNewOrganizer();

        when(tripRepository.findById(tripId)).thenReturn(Optional.of(trip));
        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));
        when(tripRepository.save(trip)).thenReturn(trip);
        when(userRepository.save(userEntity)).thenReturn(testUser);
        when(modelMapper.map(trip, TripDetailsDto.class)).thenReturn(tripDto);

        // When

        Response response = underTest.addNewOrganizerById(tripId, userId);

        // Then

        Assertions.assertThat(
                response.toResponseEntity()
                        .getStatusCode()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(
                response.toResponseEntity()
                        .getBody())
                .isEqualToComparingFieldByField(tripDto);

    }

    @Test
    public void itShouldTryAddNewOrganizerAndReturnUserNotFound(){
        // Given
        Long userId = 2L;
        Long tripId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When

        Response response = underTest.addNewOrganizerById(tripId, userId);

        // Then

        Assertions.assertThat(
                response.toResponseEntity()
                        .getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

    }

    @Test
    public void itShouldTryAddNewOrganizerAndReturnTripNotFound(){
        // Given
        Long userId = 2L;
        Long tripId = 1L;

        when(tripRepository.findById(userId)).thenReturn(Optional.empty());

        // When

        Response response = underTest.addNewOrganizerById(tripId, userId);

        // Then

        Assertions.assertThat(
                response.toResponseEntity()
                        .getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

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

    private UserEntity getTestUserEntity2(){
        UserEntity userEntity = new UserEntity();
        userEntity.setId(2L);
        userEntity.setUsername("test_user_2");
        userEntity.setPassword("password_2");
        userEntity.setFirstName("Adam");
        userEntity.setLastName("Paciorek");
        userEntity.setEmail("test2@mail.com");
        userEntity.setPhoneNumber("2234516");
        return userEntity;
    }

    private UserDetailsDto getTestUserDetailsDto2(){
        return new UserDetailsDto(
                2L,
                "test_user_2",
                "Adam",
                "Paciorek",
                "test2@gmail.com",
                "2234516"

        );
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
    private TripDetailsDto getTestTripDetailsDtoWithNewOrganizer(){
        return new TripDetailsDto(
                1L,
                "Holidays 2020",
                "Friends meet after years",
                LocalDate.parse("2021-05-12"),
                LocalDate.parse("2021-05-19"),
                getTestLocationDetailsDto(),
                getTestNewOrganizersList(),
                getTestParticipantsList()
        );
    }


    private List<UserDetailsDto> getTestOrganizersList(){
        List<UserDetailsDto> list = new ArrayList<>();
        list.add(getTestUserDetailsDto());
        return list;
    }

    private List<UserDetailsDto> getTestNewOrganizersList(){
        List<UserDetailsDto> list = getTestOrganizersList();
        list.add(getTestUserDetailsDto2());
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
