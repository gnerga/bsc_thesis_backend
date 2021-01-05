package com.nerga.travelCreatorApp.location;

import com.nerga.travelCreatorApp.common.propertymap.ApplicationPropertyMaps;
import com.nerga.travelCreatorApp.common.response.Response;
import com.nerga.travelCreatorApp.location.dto.LocationAddressCreateDto;
import com.nerga.travelCreatorApp.location.dto.LocationAddressDetailsDto;
import com.nerga.travelCreatorApp.location.dto.LocationCreateDto;
import com.nerga.travelCreatorApp.location.dto.LocationDetailsDto;
import com.nerga.travelCreatorApp.security.GeneralUserService;
import com.nerga.travelCreatorApp.security.auth.User;
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
import org.mockito.runners.MockitoJUnitRunner;
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
        LocationCreateDto locationCreateDto = returnLocationCreateDto("test", "desc", "url");
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
        LocationCreateDto locationCreateDto = returnLocationCreateDto("test", "desc", "url");
        Location location_1 = getTestLocation();

        when(modelMapper.map(locationCreateDto, Location.class)).thenReturn(location_1);
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

        LocationDetailsDto tLocationDetailsDto = getLocationDetailsDto();
        Location tLocation = getTestLocation();
        Location tUpdatedLocation = getUpdatedTestLocation();


        when(locationRepository.findById(tLocationId))
                .thenReturn(Optional.of(tLocation));


        when(locationRepository.save(tUpdatedLocation))
                .thenReturn(tUpdatedLocation);

        Response response = underTest.updateLocationById(tLocationId, tLocationDetailsDto);

        //Assertions.assertThat(response.toResponseEntity().getStatusCode()).isEqualTo(HttpStatus.OK);
        //Assertions.assertThat(response.toResponseEntity().getBody()).isEqualToComparingFieldByField(tUpdatedLocation);


    }

    @Test
    void shouldRemoveLocationWithGivenId(){
        long tLocationId = 1;
        Location tLocation = getTestLocation();
        when(locationRepository.findById(tLocationId)).thenReturn(Optional.of(tLocation));
        Response response = underTest.deleteLocationById(tLocationId);
        Assertions.assertThat(response.toResponseEntity().getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    private LocationCreateDto returnLocationCreateDto(String name,
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

    private UserDetailsDto getTestUserDetailsDto(){
        return new UserDetailsDto(
                1L,
        "test_user",
        "Jan",
        "Nowak",
        "test@mail.com",
        "1234516");
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
        location.setGoogleMapUrl("htttp/222/222");
        location.setLocationAddress(getTestLocationAddress());
        location.setOwnerEntity(getTestUserEntity());
        location.setLocationDescription("Super miejscówa, ziom");
        location.setLocationId(1L);
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
                "90-156"
        );
    }

    private LocationDetailsDto getLocationDetailsDto(){
        LocationDetailsDto location = new LocationDetailsDto();
        location.setLocationName("Mazury 2k21");
        location.setGoogleMapUrl("htttp/222/222");
        location.setLocationAddress(modelMapper.map(getTestLocationAddress(), LocationAddressDetailsDto.class));
        location.setOwner(modelMapper.map(getTestUserEntity(), UserDetailsDto.class));
        location.setLocationDescription("Super miejscówa, ziom");
        location.setLocationId(1L);
        location.setIsPrivate(false);
        return location;
    }

    private LocationAddressDetailsDto getLocationAddressDetailsDto(){
        return new LocationAddressDetailsDto();
    }

    private UserDetailsDto getUserDetailsDto(){
        return new UserDetailsDto();
    }

    private Location getUpdatedTestLocation(){
        Location location = new Location();
        location.setLocationName("Mazury 2k21");
        location.setGoogleMapUrl("htttp/222/222");
        location.setLocationAddress(getTestLocationAddress());
        location.setOwnerEntity(getTestUserEntity());
        location.setLocationDescription("Super miejscówa, ziom");
        location.setLocationId(1L);
        return location;
    }

}
