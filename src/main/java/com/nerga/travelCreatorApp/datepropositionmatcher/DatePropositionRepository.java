package com.nerga.travelCreatorApp.datepropositionmatcher;

import com.nerga.travelCreatorApp.trip.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DatePropositionRepository extends JpaRepository<DateProposition, Long> {

    List<DateProposition> findAllByOwnerIdAndTrip(Long ownerId, Trip trip);

}
