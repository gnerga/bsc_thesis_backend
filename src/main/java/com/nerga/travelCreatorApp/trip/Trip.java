package com.nerga.travelCreatorApp.trip;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.nerga.travelCreatorApp.datepropositionmatcher.DateProposition;
import com.nerga.travelCreatorApp.datepropositionmatcher.DatePropositionMatcher;
import com.nerga.travelCreatorApp.location.Location;
import com.nerga.travelCreatorApp.security.auth.database.UserEntity;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tripId;

    @NotNull
    private String tripName;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "trip_location",
            joinColumns = @JoinColumn(name = "trip_id"),
            inverseJoinColumns = @JoinColumn(name = "location_id"))
    private Location location;

    @NotNull
    private String tripDescription;
    @NotNull
    private boolean isActiveTrip;
    @NotNull
    private int tripLength;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @OneToOne
    @JoinColumn(name = "datePropositionMatcher_id", referencedColumnName = "id")
    private DatePropositionMatcher datePropositionMatcher;

    public Trip(
            Long tripId,
                String tripName,
                Location location,
                String tripDescription,
                boolean isActiveTrip,
                int maxNumberOfGivenDatePropositions,
                LocalDate startDate,
                LocalDate endDate,
                LocalDate deadLine) {

        this.tripId = tripId;
        this.tripName = tripName;
        this.location = location;
        this.tripDescription = tripDescription;
        this.isActiveTrip = isActiveTrip;
        this.tripLength = maxNumberOfGivenDatePropositions;
        this.startDate = startDate;
        this.endDate = endDate;

        this.datePropositionMatcher = new DatePropositionMatcher(
                maxNumberOfGivenDatePropositions,
                deadLine
                );
        this.organizers = new ArrayList<>();
        this.participants = new ArrayList<>();

    }

    @JsonManagedReference
    @ManyToMany(mappedBy = "organizedTrips")
//    @Singular
    private List<UserEntity> organizers;

    //@JsonManagedReference
    @ManyToMany(mappedBy = "participatedTrips")
//    @Singular
    private List<UserEntity> participants;



    public void addOrganizer(UserEntity user) {
        if (organizers == null) {
            organizers = new ArrayList<>();
        }
        organizers.add(user);
        user.getOrganizedTrips().add(this);
    }

    public void removeOrganizer(UserEntity user) {
        organizers.remove(user);
        user.getOrganizedTrips().remove(this);
    }

    public void addParticipant(UserEntity user) {
        if (participants == null) {
            participants = new ArrayList<>();
        }
        participants.add(user);
        user.getParticipatedTrips().add(this);
    }

    public void removeParticipant(UserEntity user){
        participants.remove(user);
        user.getParticipatedTrips().remove(this);
    }

    public void addDateProposition(DateProposition dateProposition){
           datePropositionMatcher.addDateProposition(dateProposition);
    }

    public boolean removeDateProposition(DateProposition dateProposition) {
        return datePropositionMatcher.removeDateProposition(dateProposition);
    }

}
