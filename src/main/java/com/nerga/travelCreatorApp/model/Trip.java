package com.nerga.travelCreatorApp.model;

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
    private Long tripId;

    private String tripName;
    @ManyToOne
    private Location location;
    private String tripDescription;

    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToMany
    private List<User> organizers;
    @ManyToMany
    private List<User> members;

}
