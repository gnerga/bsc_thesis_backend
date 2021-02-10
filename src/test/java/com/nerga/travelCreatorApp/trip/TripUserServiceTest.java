package com.nerga.travelCreatorApp.trip;

import com.nerga.travelCreatorApp.common.propertymap.ApplicationPropertyMaps;
import com.nerga.travelCreatorApp.datepropositionmatcher.DateProposition;
import com.nerga.travelCreatorApp.datepropositionmatcher.dto.DatePropositionDto;
import com.nerga.travelCreatorApp.datepropositionmatcher.dto.DatePropositionReturnedListDto;
import com.nerga.travelCreatorApp.location.LocationRepository;
import com.nerga.travelCreatorApp.security.auth.database.UserRepository;
import com.nerga.travelCreatorApp.trip.dto.TripDetailsDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

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

            Long tripId;

            DatePropositionDto testDatePropositionDto;
            DateProposition dateProposition;

            Trip testTrip;
            Trip updatedTrip;

            TripDetailsDto updatedTripDetails;
            DatePropositionReturnedListDto datePropositionReturnedListDto;


        // When
        // Then

    }

}
