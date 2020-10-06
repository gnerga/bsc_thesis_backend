package com.nerga.travelCreatorApp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tripId;

    private String tripName;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "trip_location",
            joinColumns = @JoinColumn(name = "trip_id"),
            inverseJoinColumns = @JoinColumn(name = "location_id"))
    private Location location;
    private String tripDescription;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    //@JsonManagedReference
    @ManyToMany(mappedBy = "organizedTrips")
//    @Singular
    private List<User> organizers;

    //@JsonManagedReference
    @ManyToMany(mappedBy = "usersTrips")
//    @Singular
    private List<User> members;

    public void addOrganizer(User user) {
        if (organizers == null) {
            organizers = new ArrayList<>();
        }
        organizers.add(user);
        user.getOrganizedTrips().add(this);
    }

    public void removeOrganizer(User user) {
        organizers.remove(user);
        user.getOrganizedTrips().remove(this);
    }

    public void addParticipant(User user) {
        if (members == null) {
            members = new ArrayList<>();
        }
        members.add(user);
        user.getUsersTrips().add(this);
    }
}
