package com.nerga.travelCreatorApp.location;

import com.nerga.travelCreatorApp.common.response.Response;
import com.nerga.travelCreatorApp.location.dto.LocationCreateDto;
import com.nerga.travelCreatorApp.location.dto.LocationDetailsDto;
import com.nerga.travelCreatorApp.security.GeneralUserService;
import com.nerga.travelCreatorApp.security.auth.User;
import com.nerga.travelCreatorApp.security.auth.database.UserEntity;
import com.nerga.travelCreatorApp.security.auth.database.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class LocationServiceTest {

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @Captor
    private ArgumentCaptor<Location> locationArgumentCaptor;


    private LocationService underTest;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        underTest = new LocationService(
                locationRepository,
                userRepository,
                modelMapper);
    }

    @Test
    public void itShouldSaveNewLocation(){

        // Given
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setUsername("test_user");
        userEntity.setPassword("password_1");
        userEntity.setFirstName("Jan");
        userEntity.setLastName("Nowak");
        userEntity.setEmail("test@mail.com");
        userEntity.setPhoneNumber("1234516");
        userEntity.setOrganizedTrips(null);
        userEntity.setParticipatedTrips(null);


        LocationCreateDto locationCreateDto = new LocationCreateDto();
        locationCreateDto.setLocationName("Test_1");
        locationCreateDto.setLocationDescription("Description_1");
        locationCreateDto.setGoogleMapUrl("urlAddress_1");

        Location location_1 = new Location();
        location_1.setLocationName("Test_1");
        location_1.setLocationDescription("Description_1");
        location_1.setGoogleMapUrl("urlAddress_1");

        given(modelMapper.map(locationCreateDto, Location.class)).willReturn(location_1);
        when(userRepository.findById(locationCreateDto.getOwner().getId())).thenReturn(Optional.of(userEntity));

        // When

        underTest.createNewLocation(locationCreateDto);

        // Then

        then(locationRepository).should().save(locationArgumentCaptor.capture());
        Location locationArgumentCaptureValue = locationArgumentCaptor.getValue();
        Assertions.assertThat(locationArgumentCaptureValue).isEqualTo(location_1);

    }

    @Test
    public void itShouldRequestCode200(){

        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setUsername("test_user");
        userEntity.setPassword("password_1");
        userEntity.setFirstName("Jan");
        userEntity.setLastName("Nowak");
        userEntity.setEmail("test@mail.com");
        userEntity.setPhoneNumber("1234516");
        userEntity.setOrganizedTrips(null);
        userEntity.setParticipatedTrips(null);

        LocationCreateDto locationCreateDto = returnLocationCreateDto(
                "Test_1",
                "Description_1",
                "urlAddress_1"
        );

        Location location_1 = returnLocation(
                1L,
                "Test_1",
                "Description_1",
                "urlAddress_1"
        );

        given(modelMapper.map(locationCreateDto, Location.class)).willReturn(location_1);
        given(locationRepository.save(location_1)).willReturn(location_1);
        when(userRepository.findById(locationCreateDto.getOwner().getId())).thenReturn(Optional.of(userEntity));

        // When

        Response response= underTest.createNewLocation(locationCreateDto);

        // Then

        Assertions.assertThat(response
                .toResponseEntity()
                .getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);

        Assertions.assertThat(response
                .toResponseEntity()
                .getBody()).isEqualToComparingFieldByField(location_1);


    }

    @Test
    public void shouldReturnAllLocationsWithDescriptionsContainsGivenPhrase(){

        // Given
        Location location_1 = returnLocation(
                1L,
                "Test_1",
                "Description_1",
                "urlPath.com");

        LocationDetailsDto locationDetails_1 = returnLocationDetailsDto(
                1L,
                "Test_1",
                "Description_1",
                "urlPath.com");


        Location location_2 = returnLocation(
                2L,
                "Test_2",
                "Description_2",
                "urlPath.com");

        LocationDetailsDto locationDetails_2 = returnLocationDetailsDto(
                2L,
                "Test_2",
                "Description_2",
                "urlPath.com");



        String givenPhrase = "description";

        List<Location> listOfLocation = new ArrayList<>();
        listOfLocation.add(location_1);
        listOfLocation.add(location_2);

        List<LocationDetailsDto> locationDetailsDtoList = new ArrayList<>();
        locationDetailsDtoList.add(locationDetails_1);
        locationDetailsDtoList.add(locationDetails_2);

        given(locationRepository.findAll()).willReturn(listOfLocation);
        given(modelMapper.map(location_1, LocationDetailsDto.class)).willReturn(locationDetails_1);
        given(modelMapper.map(location_2, LocationDetailsDto.class)).willReturn(locationDetails_2);
        // When
        Response response = underTest.findAllWithDescription(givenPhrase);
        // Then

        Assertions.assertThat(response
                .toResponseEntity()
                .getStatusCode()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(response
                .toResponseEntity()
                .getBody()).isEqualToComparingFieldByField(locationDetailsDtoList);

    }

    @Test
    public void shouldReturnAllLocationsWithNameContainsGivenPhrase(){

        // Given
        Location location_1 = returnLocation(
                1L,
                "Test_1",
                "Description_1",
                "urlPath.com");

        LocationDetailsDto locationDetails_1 = returnLocationDetailsDto(
                1L,
                "Test_1",
                "Description_1",
                "urlPath.com");


        Location location_2 = returnLocation(
                2L,
                "testing_2",
                "Description_2",
                "urlPath.com");

        LocationDetailsDto locationDetails_2 = returnLocationDetailsDto(
                2L,
                "testing_2",
                "Description_2",
                "urlPath.com");


        String givenPhrase = "test";

        List<Location> listOfLocation = new ArrayList<>();
        listOfLocation.add(location_1);
        listOfLocation.add(location_2);

        List<LocationDetailsDto> locationDetailsDtoList = new ArrayList<>();
        locationDetailsDtoList.add(locationDetails_1);
        locationDetailsDtoList.add(locationDetails_2);

        given(locationRepository.findAll()).willReturn(listOfLocation);
        given(modelMapper.map(location_1, LocationDetailsDto.class)).willReturn(locationDetails_1);
        given(modelMapper.map(location_2, LocationDetailsDto.class)).willReturn(locationDetails_2);
        // When
        Response response = underTest.findAllLocationsWithLocationName(givenPhrase);
        // Then

        Assertions.assertThat(response
                .toResponseEntity()
                .getStatusCode()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(response
                .toResponseEntity()
                .getBody()).isEqualToComparingFieldByField(locationDetailsDtoList);




    }

    private LocationCreateDto returnLocationCreateDto(String name,
                                                      String description,
                                                      String urlPath){

        LocationCreateDto locationCreateDto = new LocationCreateDto();
        locationCreateDto.setLocationName(name);
        locationCreateDto.setLocationDescription(description);
        locationCreateDto.setGoogleMapUrl(urlPath);

        return locationCreateDto;

    }

    private Location returnLocation(
                                        Long id,
                                        String name,
                                        String description,
                                        String urlPath
    ){

        Location location = new Location();
        location.setLocationId(id);
        location.setLocationName(name);
        location.setLocationDescription(description);
        location.setGoogleMapUrl(urlPath);

        return location;

    }

    private LocationDetailsDto returnLocationDetailsDto(
            Long id,
            String name,
            String description,
            String urlPath
    ){
        LocationDetailsDto locationDetailsDto = new LocationDetailsDto();
        locationDetailsDto.setLocationId(id);
        locationDetailsDto.setLocationName(name);
        locationDetailsDto.setLocationDescription(description);
        locationDetailsDto.setGoogleMapUrl(urlPath);
        return locationDetailsDto;
    }

}
