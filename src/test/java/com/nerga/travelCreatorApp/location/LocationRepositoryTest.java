package com.nerga.travelCreatorApp.location;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

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
        void itShouldSaveLocation() {

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

}
