package com.nerga.travelCreatorApp.trip;

import com.nerga.travelCreatorApp.common.propertymap.ApplicationPropertyMaps;
import com.nerga.travelCreatorApp.common.response.Response;
import com.nerga.travelCreatorApp.datepropositionmatcher.DateProposition;
import com.nerga.travelCreatorApp.datepropositionmatcher.dto.DatePropositionDto;
import com.nerga.travelCreatorApp.datepropositionmatcher.dto.DatePropositionReturnDto;
import com.nerga.travelCreatorApp.datepropositionmatcher.dto.DatePropositionReturnedListDto;
import com.nerga.travelCreatorApp.expensesregister.ExpenseRecord;
import com.nerga.travelCreatorApp.expensesregister.Expenses;
import com.nerga.travelCreatorApp.expensesregister.dto.*;
import com.nerga.travelCreatorApp.location.Location;
import com.nerga.travelCreatorApp.location.LocationRepository;
import com.nerga.travelCreatorApp.location.address.LocationAddress;
import com.nerga.travelCreatorApp.location.address.dto.LocationAddressDetailsDto;
import com.nerga.travelCreatorApp.location.dto.LocationDetailsDto;
import com.nerga.travelCreatorApp.security.auth.database.UserEntity;
import com.nerga.travelCreatorApp.security.auth.database.UserRepository;
import com.nerga.travelCreatorApp.security.dto.UserDetailsDto;
import com.nerga.travelCreatorApp.trip.dto.TripUserAndDetailsDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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


            TripUserAndDetailsDto updatedTripDetails = getUpdatedTestTripDetailsDto2();
            DatePropositionReturnedListDto datePropositionReturnedListDto = getDatePropositionReturned();

            testUpdatedTrip.addDateProposition(dateProposition);

            when(tripRepository.findById(any(Long.class))).thenReturn(Optional.of(testTrip));
            when(modelMapper.map(testDatePropositionDto, DateProposition.class)).thenReturn(dateProposition);
            when(tripRepository.save(any(Trip.class))).thenReturn(testUpdatedTrip);
            when(modelMapper.map(testUpdatedTrip, TripUserAndDetailsDto.class)).thenReturn(updatedTripDetails);

        // When

            Response response = underTest.addNewDateProposition(testDatePropositionDto, tripId);

        // Then


        Assertions.assertThat(
                response.toResponseEntity()
                        .getStatusCode()).isEqualTo(HttpStatus.OK);


    }

    @Test
    void shouldTryCreateNewExpenseAndReturnThatTripNotFound(){
        ExpensesCreateDto expensesCreateDto = getTestExpenseCreateDto();
        when(tripRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        Response response = underTest.createExpense(expensesCreateDto);
        Assertions.assertThat(
                response.toResponseEntity()
                        .getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        Assertions.assertThat(
                response.toResponseEntity()
                        .getBody())
                .isEqualToComparingFieldByField("TRIP_NOT_FOUND");

    }

    @Test
    void shouldTryCreateNewExpenseAndReturnThatUserNotFound(){

        Trip trip = getTestTrip();

        ExpensesCreateDto expensesCreateDto = getTestExpenseCreateDto();

        when(tripRepository.findById(any(Long.class))).thenReturn(Optional.of(trip));
        when(userRepository.existsById(any(Long.class))).thenReturn(false);

        Response response = underTest.createExpense(expensesCreateDto);
        Assertions.assertThat(
                response.toResponseEntity()
                        .getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        Assertions.assertThat(
                response.toResponseEntity()
                        .getBody())
                .isEqualToComparingFieldByField("USER_NOT_FOUND");

    }

    @Test
    void shouldCreateExpenseByTripId(){
        // Given

        Trip testTrip = getTestTrip();
        ExpensesCreateDto testExpensesCreateDto = getTestExpenseCreateDto();
//        Expenses testExpense = getTestExpense();
        ExpensesDetailsDto testExpensesDetailsDto = getTestExpensesDetailsDto();

        when(tripRepository.findById(any(Long.class))).thenReturn(Optional.of(testTrip));
        when(userRepository.existsById(any(Long.class))).thenReturn(true);
        when(tripRepository.save(any(Trip.class))).thenReturn(testTrip);
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(getTestUserEntity()));
        when(modelMapper.map(getTestUserEntity(), UserDetailsDto.class)).thenReturn(getTestUserDetailsDto());

        // When

        Response response = underTest.createExpense(testExpensesCreateDto);

        // Then

        Assertions.assertThat(
                response.toResponseEntity()
                        .getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.toResponseEntity().getBody()).hasNoNullFieldsOrProperties();

    }

    @Test
    public void shouldUpdateExpense(){
//        ExpenseUpdateDto updateDto = new ExpenseUpdateDto(1L, 0L, "New title", "New description");
//        Trip trip = getTestTrip();
//        trip.getExpenseManager().addExpenses(getTestExpense());
//        when(tripRepository.findById(updateDto.getTripId())).thenReturn(Optional.of(trip));
//        underTest.updateExpenseById(updateDto);
    }

// -----------------------------------------------------------------

    private ExpensesDetailsDto getTestExpensesDetailsDto(){

        List<ExpenseRecordDetailsDto> list = new ArrayList<>();
        list.add(new ExpenseRecordDetailsDto(1L, getTestUserDetailsDto(), 50));
//        list.add(new ExpenseRecordDetailsDto(2L, getTestUserDetailsDto_2(), 70));
        return new ExpensesDetailsDto(
                0L,
                "Składka",
                "Zakupy, opłaty dodatkowe",
                50,
                list
        );
    }

    private ExpensesCreateDto getTestExpenseCreateDto(){
        return new ExpensesCreateDto(
            1L,
                "Składka",
                "Zakupy, opłaty dodatkowe",
                50,
                getExpenseRecordDtoList()

        );
    }

    private List<ExpenseRecordCreateDto> getExpenseRecordDtoList(){
        List<ExpenseRecordCreateDto> list = new ArrayList<>();
        list.add(new ExpenseRecordCreateDto(1L, 50));
//        list.add(new ExpenseRecordCreateDto(2L, 70));
        return list;
    }

    private Expenses getTestExpense(){
        return new Expenses(
                "Składka",
                "Zakupy, opłaty dodatkowe",
                120,
                getExpenseRecordList()
        );
    }

    private List<ExpenseRecord> getExpenseRecordList(){
        List<ExpenseRecord> list = new ArrayList<>();
        list.add(new ExpenseRecord(getTestUserEntity(), 50));
//        list.add(new ExpenseRecord(getTestUserEntity_2(), 70));
        return list;
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

    private UserEntity getTestUserEntity_2(){
        UserEntity userEntity = new UserEntity();

        userEntity.setId(2L);
        userEntity.setUsername("test_user_2");
        userEntity.setPassword("password_1");
        userEntity.setFirstName("Dan");
        userEntity.setLastName("Kovalski");
        userEntity.setEmail("test2@mail.com");
        userEntity.setPhoneNumber("2234516");

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

    private UserDetailsDto getTestUserDetailsDto_2(){
        return new UserDetailsDto(
                2L,
                "test_user2",
                "Dan",
                "Kovalski",
                "test2@gmail.com",
                "2234516"

        );
    }

    private List<UserDetailsDto> getTestParticipantsList(){
        List<UserDetailsDto> list = new ArrayList<>();
        return list;
    }

    private TripUserAndDetailsDto getUpdatedTestTripDetailsDto2(){
        return new TripUserAndDetailsDto(
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

    private DatePropositionReturnedListDto getDatePropositionReturned(){
        DateProposition proposition = new DateProposition(
                LocalDate.parse("2021-06-12"),
                LocalDate.parse( "2021-06-19"),
                "test_name",
                1L
        );
        List<DatePropositionReturnDto> list = new ArrayList<DatePropositionReturnDto>();
        list.add(new DatePropositionReturnDto(proposition.datePropositionToString(), 0.0));

        return new DatePropositionReturnedListDto(
                proposition.getStartDate().toString(),
                proposition.getEndDate().toString(),
                list
        );
    }

}
