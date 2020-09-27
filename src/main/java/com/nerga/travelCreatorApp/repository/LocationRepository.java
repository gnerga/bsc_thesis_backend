package com.nerga.travelCreatorApp.repository;

import com.nerga.travelCreatorApp.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    Optional<Location> findLocationByLocationName(String locationName);
    Optional<Location> findLocationByLocationDescriptionContains(String locationDetail);

    boolean existsLocationByLocationName(String locationName);

}
