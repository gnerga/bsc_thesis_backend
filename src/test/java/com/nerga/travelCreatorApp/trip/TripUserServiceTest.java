package com.nerga.travelCreatorApp.trip;

import com.nerga.travelCreatorApp.common.propertymap.ApplicationPropertyMaps;
import com.nerga.travelCreatorApp.datepropositionmatcher.DateProposition;
import com.nerga.travelCreatorApp.datepropositionmatcher.dto.DatePropositionDto;
import com.nerga.travelCreatorApp.datepropositionmatcher.dto.DatePropositionReturnDto;
import com.nerga.travelCreatorApp.datepropositionmatcher.dto.DatePropositionReturnedListDto;
import com.nerga.travelCreatorApp.location.Location;
import com.nerga.travelCreatorApp.location.LocationRepository;
import com.nerga.travelCreatorApp.location.address.LocationAddress;
import com.nerga.travelCreatorApp.location.address.dto.LocationAddressDetailsDto;
import com.nerga.travelCreatorApp.location.dto.LocationDetailsDto;
import com.nerga.travelCreatorApp.security.auth.database.UserEntity;
import com.nerga.travelCreatorApp.security.auth.database.UserRepository;
import com.nerga.travelCreatorApp.security.dto.UserDetailsDto;
import com.nerga.travelCreatorApp.trip.dto.TripDetailsDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TripUserServiceTest {

    @Mock
    TripRepository tripRepository;
    @Mock
    LocationRepository locationRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    ModelMapper modelMapper;

    TripUserService underTest;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        modelMapper.addMappings(ApplicationPropertyMaps.userEntityFieldMapping());
        underTest = new TripUserService(
                tripRepository,
                locationRepository,
                userRepository,
                modelMapper
        );
    }

    @Test
    public void shouldAddDatePropositionToDateMatcher(){

        // Given

            Long tripId = 1L;

            DatePropositionDto testDatePropositionDto = getTestDatePropositionDto();
            DateProposition dateProposition = getTestDateProposition();

            Trip testTrip = getTestTrip();
            Trip testUpdatedTrip = getTestUpdatedTrip();
            System.out.println(testTrip.getDatePropositionMatcher().getDatePropositionList().size());
            Trip updatedTrip = getTestUpdatedTrip();

            TripDetailsDto updatedTripDetails = getUpdatedTestTripDetailsDto2();
            DatePropositionReturnedListDto datePropositionReturnedListDto = getDatePropositionReturnedList();


        // When



        // Then



    }

// -----------------------------------------------------------------

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

    private Trip getTestUpdatedTrip(){
        return new Trip(
                1L,
                "Holidays 2020",
                getTestLocation(),
                "Friends meet after years",
                true,
                7,
                LocalDate.parse("2021-06-12"),
                LocalDate.parse("2021-06-19"),
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

    private List<UserDetailsDto> getTestOrganizersList(){
        List<UserDetailsDto> list = new ArrayList<>();
        list.add(getTestUserDetailsDto());
        return list;
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

    private List<UserDetailsDto> getTestParticipantsList(){
        List<UserDetailsDto> list = new ArrayList<>();
        return list;
    }

    private TripDetailsDto getUpdatedTestTripDetailsDto2(){
        return new TripDetailsDto(
                1L,
                "Holidays 2021",
                "Friends meet after months",
                LocalDate.parse("2021-06-12"),
                LocalDate.parse("2021-06-19"),
                getTestLocationDetailsDto(),
                getTestOrganizersList(),
                getTestParticipantsList()
        );
    }

    private DateProposition getTestDateProposition(){
        return new
                DateProposition(LocalDate.parse("2021-06-12"),
                LocalDate.parse("2021-06-19"), "test_user", 1L);
    }

    private DatePropositionDto getTestDatePropositionDto(){
        return new DatePropositionDto(
                "2021-06-12",
                "2021-06-19",
                "test_user",
                1L
        );
    }

    private DatePropositionReturnedListDto getDatePropositionReturnedList(){
        List<DatePropositionReturnDto> list = new ArrayList<DatePropositionReturnDto>();
        list.add(new DatePropositionReturnDto("From: 16 OCTOBER 2020 To: 22 OCTOBER 2020", 0.8333333333333333));
        list.add(new DatePropositionReturnDto("From: 18 OCTOBER 2020 To: 25 OCTOBER 2020", 0.5714285714285714));
        list.add(new DatePropositionReturnDto("From: 10 OCTOBER 2020 To: 17 OCTOBER 2020",0.14285714285714285));


        return new DatePropositionReturnedListDto(
                "2020-10-16",
                "2020-10-22",
                list
        );
    }

}
