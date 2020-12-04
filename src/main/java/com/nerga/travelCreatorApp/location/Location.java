package com.nerga.travelCreatorApp.location;

import com.nerga.travelCreatorApp.security.auth.database.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long locationId;

    private String locationName;
    private String locationDescription;
    private String googleMapUrl;

//    @ManyToOne
//    private UserEntity ownerEntity;
//
//    // TODO gnerga dodać holder na zdjęcia

}
