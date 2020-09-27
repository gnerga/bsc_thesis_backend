package com.nerga.travelCreatorApp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Location {

    @Id
    private Long locationId;

    private String locationName;
    private String locationDescription;
    private String googleMapUrl;

}
