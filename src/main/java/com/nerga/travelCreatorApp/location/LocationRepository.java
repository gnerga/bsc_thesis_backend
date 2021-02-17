package com.nerga.travelCreatorApp.location;

import com.nerga.travelCreatorApp.security.auth.database.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    List<Location> findLocationsByLocationNameContains(String locationName);
    List<Location> findLocationsByLocationDescriptionContains(String locationDetail);
    List<Location> findLocationByLocationDescriptionAndOwner(String locationDetail, UserEntity owner);

    List<Location> findLocationByOwner(UserEntity userEntity);

    boolean existsLocationByLocationName(String locationName);

}
