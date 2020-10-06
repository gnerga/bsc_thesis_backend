package com.nerga.travelCreatorApp.repository;

import com.nerga.travelCreatorApp.model.Trip;
import com.nerga.travelCreatorApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TripRepository extends JpaRepository<Trip,  Long> {

    Optional<Trip> findTripByTripName(String tripName);
    Optional<Trip> findTripByTripDescriptionContains(String word);

    Optional<List<Trip>> findByOrganizersContaining(User user);
    Optional<List<Trip>> findByMembersContaining(User user);

    Optional<List<Trip>> findTripsByStartDateAndEndDate(LocalDate startDate,
                                                        LocalDate endDate);

}
