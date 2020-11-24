package com.nerga.travelCreatorApp.trip;

import com.nerga.travelCreatorApp.security.auth.database.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

//@Repository
public interface TripRepository extends JpaRepository<Trip,  Long> {

    Optional<Trip> findTripByTripName(String tripName);
    Optional<Trip> findTripByTripDescriptionContains(String word);

    Optional<List<Trip>> findByOrganizersContaining(UserEntity user);
    Optional<List<Trip>> findByParticipantsContaining(UserEntity user);

    Optional<List<Trip>> findTripsByStartDateAndEndDate(LocalDate startDate,
                                                        LocalDate endDate);

}
