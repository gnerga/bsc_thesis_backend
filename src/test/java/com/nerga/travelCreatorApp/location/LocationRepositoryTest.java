package com.nerga.travelCreatorApp.location;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

//todo doczytać o tym
@DataJpaTest(
        properties = {
                "spring.jpa.properties.javax.persistence.validation.mode=none"
        }
)
public class LocationRepositoryTest {

        @Autowired
        private LocationRepository underTest;

        @Test
        void itShouldSaveLocationAndFindById() {

                // Given
                Location location = new Location();
                location.setLocationName("Test");
                location.setLocationDescription("Description");
                location.setGoogleMapUrl("urlAddress");

                // When
                Location locationReturned = underTest.save(location);

                //Then
                Optional<Location> optionalLocation = underTest.findById(locationReturned.getLocationId());
                assertThat(optionalLocation)
                        .isPresent()
                        .hasValueSatisfying(
                                location1 -> {
                                        assertThat(location1).isEqualToComparingFieldByField(locationReturned);
                                }
                        );
        }

        @Test
        void itShouldReturnListOfAllCreatedLocation(){
                // Given

                Location location_1 = new Location();
                location_1.setLocationName("Test_1");
                location_1.setLocationDescription("Description_1");
                location_1.setGoogleMapUrl("urlAddress_1");

                Location location_2 = new Location();
                location_2.setLocationName("Test_2");
                location_2.setLocationDescription("Description_2");
                location_2.setGoogleMapUrl("urlAddress_2");

                // When

                underTest.save(location_1);
                underTest.save(location_2);
                List<Location> listOptionalLocation = underTest.findAll();

                // Then

                assertEquals(2, listOptionalLocation.size());
        }

        @ParameterizedTest
        @CsvSource(
                {
                 "Description_1, Description_2, 2, 1",
                 "Rejs żeglarski, Rejs motorowodny, Rejs, 2",
                 "Rejs żeglarski, Rejs motorowodny, motorowodny, 1",
                 "Rejs żeglarski, Rejs motorowodny, rejs, 0"
                }
        )
        void shouldReturnListOfLocationContainsWordInLocationDescription(
                String description_1,
                String description_2,
                String fragmentOfText,
                int expectedResult
                ){

                // When

                Location location_1 = new Location();
                location_1.setLocationName("Test_1");
                location_1.setLocationDescription(description_1);
                location_1.setGoogleMapUrl("urlAddress_1");

                Location location_2 = new Location();
                location_2.setLocationName("Test_2");
                location_2.setLocationDescription(description_2);
                location_2.setGoogleMapUrl("urlAddress_2");

                underTest.save(location_1);
                underTest.save(location_2);

                // Given

                List<Location> foundLocation = underTest
                        .findLocationsByLocationDescriptionContains(fragmentOfText);

                // Then

                Assertions.assertEquals(expectedResult, foundLocation.size());
        }

        void shouldReturnLocationContainsWordInLocationName(){

        }



}
