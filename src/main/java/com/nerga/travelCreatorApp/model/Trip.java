package com.nerga.travelCreatorApp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonManagedReference
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinTable(name="trip_location",
            joinColumns = @JoinColumn(name="trip_id"),
            inverseJoinColumns = @JoinColumn(name="location_id"))
    private Location location;
    private String tripDescription;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @JsonManagedReference
    @ManyToMany(mappedBy = "organizedTrips")
    private List<User> organizers;

    @JsonManagedReference
    @ManyToMany(mappedBy = "usersTrips")
    private List<User> members;

}
