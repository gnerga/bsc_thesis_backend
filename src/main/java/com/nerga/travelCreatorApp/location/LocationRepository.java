package com.nerga.travelCreatorApp.location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    Location findLocationByLocationName(String locationName);
    List<Location> findLocationsByLocationDescriptionContains(String locationDetail);

//    Location findLocationByLocationId(Long id);

    boolean existsLocationByLocationName(String locationName);

}
