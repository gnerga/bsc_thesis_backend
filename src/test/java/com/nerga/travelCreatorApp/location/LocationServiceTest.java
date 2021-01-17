package com.nerga.travelCreatorApp.location;

import com.nerga.travelCreatorApp.common.propertymap.ApplicationPropertyMaps;
import com.nerga.travelCreatorApp.common.response.Response;
import com.nerga.travelCreatorApp.location.address.LocationAddress;
import com.nerga.travelCreatorApp.location.address.dto.LocationAddressCreateDto;
import com.nerga.travelCreatorApp.location.address.dto.LocationAddressDetailsDto;
import com.nerga.travelCreatorApp.location.dto.LocationCreateDto;
import com.nerga.travelCreatorApp.location.dto.LocationDetailsDto;
import com.nerga.travelCreatorApp.security.auth.database.UserEntity;
import com.nerga.travelCreatorApp.security.auth.database.UserRepository;
import com.nerga.travelCreatorApp.security.dto.UserDetailsDto;
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
        modelMapper.addMappings(ApplicationPropertyMaps.userEntityFieldMapping());
        underTest = new LocationService(
                locationRepository,
                userRepository,
                modelMapper);
    }

    @Test
    public void itShouldSaveNewLocation(){

        // Given
        UserEntity userEntity = getTestUserEntity();
        LocationCreateDto locationCreateDto = getLocationCreateDtoWithArguments("test", "desc", "url");
        Location location_1 = getTestLocation();

        when(modelMapper.map(locationCreateDto, Location.class)).thenReturn(location_1);
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

        UserEntity userEntity = getTestUserEntity();
        LocationCreateDto locationCreateDto = getLocationCreateDto();
        Location location_1 = getTestLocation();
        LocationDetailsDto locationDetailsDto = getLocationDetailsDto();

        when(modelMapper.map(locationCreateDto, Location.class)).thenReturn(location_1);
        when(modelMapper.map(location_1, LocationDetailsDto.class)).thenReturn(locationDetailsDto);
        when(userRepository.findById(locationCreateDto.getOwner().getId())).thenReturn(Optional.of(userEntity));
        when(locationRepository.save(location_1)).thenReturn(location_1);

        // When

        Response response= underTest.createNewLocation(locationCreateDto);

        // Then

        Assertions.assertThat(response
                .toResponseEntity()
                .getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);

        Assertions.assertThat(response
                .toResponseEntity()
                .getBody()).isEqualToComparingFieldByField(locationDetailsDto);


    }

    @Test
    public void shouldReturnAllLocationsWithDescriptionsContainsGivenPhrase(){

        // Given
        Location location_1 = getTestLocationWithGivenArgument(
                1L,
                "Test_1",
                "Description_1",
                "urlPath.com");

        LocationDetailsDto locationDetails_1 = getLocationDetailsDtoWithArguments(
                1L,
                "Test_1",
                "Description_1",
                "urlPath.com");


        Location location_2 = getTestLocationWithGivenArgument(
                2L,
                "Test_2",
                "Description_2",
                "urlPath.com");

        LocationDetailsDto locationDetails_2 = getLocationDetailsDtoWithArguments(
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
        Location location_1 = getTestLocationWithGivenArgument(
                1L,
                "Test_1",
                "Description_1",
                "urlPath.com");

        LocationDetailsDto locationDetails_1 = getLocationDetailsDtoWithArguments(
                1L,
                "Test_1",
                "Description_1",
                "urlPath.com");


        Location location_2 = getTestLocationWithGivenArgument(
                2L,
                "testing_2",
                "Description_2",
                "urlPath.com");

        LocationDetailsDto locationDetails_2 = getLocationDetailsDtoWithArguments(
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

        System.out.println(response.toResponseEntity().getBody());

        Assertions.assertThat(response
                .toResponseEntity()
                .getStatusCode()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(response
                .toResponseEntity()
                .getBody()).isEqualToComparingFieldByField(locationDetailsDtoList);




    }

    @Test
    void shouldReturnLocationWithGivenId(){

        // When
        long tLocationId = 1;

        Location location = getTestLocation();
        LocationDetailsDto locationDetailsDto= getLocationDetailsDto();

        when(locationRepository.findById(tLocationId)).thenReturn(Optional.of(location));
        when(modelMapper.map(location, LocationDetailsDto.class)).thenReturn(locationDetailsDto);

        // Then
        Response response = underTest.findById(tLocationId);

        // Assert
        Assertions.assertThat(response.toResponseEntity().getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.toResponseEntity().getBody()).isEqualToComparingFieldByField(locationDetailsDto);

    }

    @Test
    void shouldReturnErrorWhenNotFoundLocationWithGivenId(){

        // When
        long tLocationId = 2;

        Location location = getTestLocation();
        LocationDetailsDto locationDetailsDto= getLocationDetailsDto();

        when(locationRepository.findById(tLocationId)).thenReturn(Optional.empty());
     // Then
        Response response = underTest.findById(tLocationId);

        // Assert
        Assertions.assertThat(response.toResponseEntity().getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

    }

    @Test
    void shouldUpdateLocationAndReturnSuccess(){
        long tLocationId = 1;

        LocationDetailsDto tLocationDetailsDto = getUpdatedLocationDetailsDto();
        Location tLocation = getTestLocation();
        Location tUpdatedLocation = getUpdatedTestLocation();


        when(locationRepository.findById(tLocationId))
                .thenReturn(Optional.of(tLocation));

        when(modelMapper.map(tUpdatedLocation, LocationDetailsDto.class))
                .thenReturn(tLocationDetailsDto);

        when(locationRepository.save(tUpdatedLocation))
                .thenReturn(tUpdatedLocation);

        Response response = underTest.updateLocationById(tLocationId, tLocationDetailsDto);

        Assertions.assertThat(response.toResponseEntity().getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.toResponseEntity().getBody()).isEqualToComparingFieldByField(tLocationDetailsDto);


    }

    @Test
    void shouldDeleteLocationWithGivenId(){
        long tLocationId = 1;
        Location tLocation = getTestLocation();
        when(locationRepository.findById(tLocationId)).thenReturn(Optional.of(tLocation));
        Response response = underTest.deleteLocationById(tLocationId);
        Assertions.assertThat(response.toResponseEntity().getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    private LocationCreateDto getLocationCreateDtoWithArguments(String name,
                                                      String description,
                                                      String urlPath){

        LocationCreateDto locationCreateDto = new LocationCreateDto();
        locationCreateDto.setLocationName(name);
        locationCreateDto.setLocationDescription(description);
        locationCreateDto.setGoogleMapUrl(urlPath);
        locationCreateDto.setLocationAddress(getTestLocationAddressCreateDto());
        locationCreateDto.setOwner(getTestUserDetailsDto());

        return locationCreateDto;

    }

    private LocationCreateDto getLocationCreateDto(){

        LocationCreateDto locationCreateDto = new LocationCreateDto();
        locationCreateDto.setLocationName("Super Spot");
        locationCreateDto.setLocationDescription("Super miejscówa, ziom");
        locationCreateDto.setGoogleMapUrl("htttp/222/222");
        locationCreateDto.setLocationAddress(getTestLocationAddressCreateDto());
        locationCreateDto.setOwner(getTestUserDetailsDto());

        return locationCreateDto;

    }

    private Location getTestLocationWithGivenArgument(
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

    private LocationDetailsDto getLocationDetailsDtoWithArguments(
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
        locationDetailsDto.setLocationAddress(getLocationAddressDetailsDto());
        locationDetailsDto.setOwner(getTestUserDetailsDto());
        return locationDetailsDto;
    }



    private UserDetailsDto getTestUserDetailsDto(){
        return new UserDetailsDto(
                1L,
                "test_user",
                "Jan",
                "Nowak",
                "test@mail.com",
                "1234516");
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

    private LocationDetailsDto getLocationDetailsDto(){
        LocationDetailsDto locationDetailsDto = new LocationDetailsDto();
        locationDetailsDto.setLocationId(1L);
        locationDetailsDto.setLocationName("Super Spot");
        locationDetailsDto.setLocationDescription("Super miejscówa, ziom");
        locationDetailsDto.setGoogleMapUrl("htttp/222/222");
        locationDetailsDto.setLocationAddress(getLocationAddressDetailsDto());
        locationDetailsDto.setOwner(getTestUserDetailsDto());
        locationDetailsDto.setIsPrivate(false);
        return locationDetailsDto;
    }

    private Location getTestLocation(){
        Location location = new Location();
        location.setLocationName("Super Spot");
        location.setGoogleMapUrl("htttp/222/222");
        location.setLocationAddress(getTestLocationAddress());
        location.setOwner(getTestUserEntity());
        location.setLocationDescription("Super miejscówa, ziom");
        location.setLocationId(1L);
        location.setPrivate(false);
        return location;
    }

    private LocationAddressCreateDto getTestLocationAddressCreateDto(){
        return new LocationAddressCreateDto(
                "Poland",
                "Lodz",
                "Tunelowa",
                1,
                "m. 1",
                "90-156"
        );
    }

    private LocationAddress getTestLocationAddress(){
        return  new LocationAddress(
                1L,
                "Poland",
                "Lodz",
                "Tunelowa",
                1,
                "m. 1",
                "90-222"
        );
    }

    private LocationDetailsDto getUpdatedLocationDetailsDto(){
        LocationDetailsDto location = new LocationDetailsDto();
        location.setLocationName("Mazury 2k21");
        location.setGoogleMapUrl("htttp/222/222");
        location.setLocationAddress(getLocationAddressDetailsDto());
        location.setOwner(getTestUserDetailsDto());
        location.setLocationDescription("Super miejscówa, ziom");
        location.setLocationId(1L);
        location.setIsPrivate(false);
        return location;
    }

    private LocationAddressDetailsDto getLocationAddressDetailsDto(){
        return new LocationAddressDetailsDto(
                1L,
                "Poland",
                "Lodz",
                "Tunelowa",
                1,
                "m. 1",
                "90-222"
        );
    }



    private Location getUpdatedTestLocation(){
        Location location = new Location();
        location.setLocationName("Mazury 2k21");
        location.setGoogleMapUrl("htttp/222/222");
        location.setLocationAddress(getTestLocationAddress());
        location.setOwner(getTestUserEntity());
        location.setLocationDescription("Super miejscówa, ziom");
        location.setLocationId(1L);
        location.setPrivate(false);
        return location;
    }

}
