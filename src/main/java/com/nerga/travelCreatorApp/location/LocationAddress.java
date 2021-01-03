package com.nerga.travelCreatorApp.location;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="location_address")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long locationAddressId;
    private String countryName;
    private String cityName;
    private String street;
    private int number;
    private String numberExtension;
    private String zipCode;

}
