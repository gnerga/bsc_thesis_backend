package com.nerga.travelCreatorApp.repository;

import com.nerga.travelCreatorApp.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRepository extends JpaRepository<Trip,  Long> {
}
