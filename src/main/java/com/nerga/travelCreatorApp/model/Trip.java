package com.nerga.travelCreatorApp.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tripId;

    private String tripName;
    @ManyToOne
    private Location location;
    private String tripDescription;

    private LocalDate startDate;
    private LocalDate endDate;

    @JsonManagedReference
    @ManyToMany(mappedBy = "organizedTrips")
    private List<User> organizers;

    @JsonManagedReference
    @ManyToMany(mappedBy = "usersTrips")
    private List<User> members;

}
