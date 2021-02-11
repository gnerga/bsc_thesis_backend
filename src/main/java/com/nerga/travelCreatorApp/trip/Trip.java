package com.nerga.travelCreatorApp.trip;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.nerga.travelCreatorApp.datepropositionmatcher.DateProposition;
import com.nerga.travelCreatorApp.datepropositionmatcher.DatePropositionMatcher;
import com.nerga.travelCreatorApp.datepropositionmatcher.dto.DatePropositionReturnDto;
import com.nerga.travelCreatorApp.datepropositionmatcher.dto.DatePropositionReturnedListDto;
import com.nerga.travelCreatorApp.expensesregister.ExpensesManager;
import com.nerga.travelCreatorApp.location.Location;
import com.nerga.travelCreatorApp.post.PostManager;
import com.nerga.travelCreatorApp.security.auth.database.UserEntity;
import com.nerga.travelCreatorApp.trip.dto.TripUpdateDto;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
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

//    @OneToOne
//    @JoinColumn(name = "datePropositionMatcher_id", referencedColumnName = "id")
//    private DatePropositionMatcher datePropositionMatcher;

    @OneToMany
    private List<DateProposition> datePropositionList;

    @OneToMany
    private List<DateProposition> analyzedDatePropositionList;

    @OneToOne
    @JoinColumn(name = "expensesManager_id", referencedColumnName = "expensesManagerId")
    private ExpensesManager expenseManager;


    @OneToOne
    @JoinColumn(name = "postManager_id", referencedColumnName = "postManagerId")
    private PostManager postManager;

//    @JsonManagedReference
    @ManyToMany(mappedBy = "organizedTrips")
//    @Singular
    private List<UserEntity> organizers;

//    @JsonManagedReference
    @ManyToMany(mappedBy = "participatedTrips")
//    @Singular
    private List<UserEntity> participants;

    public Trip(
            Long tripId,
                String tripName,
                Location location,
                String tripDescription,
                boolean isActiveTrip,
                int tripLength,
                LocalDate startDate,
                LocalDate endDate,
                Long creatorId
                ) {

        this.tripId = tripId;
        this.tripName = tripName;
        this.location = location;
        this.tripDescription = tripDescription;
        this.isActiveTrip = isActiveTrip;
        this.tripLength = tripLength;
        this.startDate = startDate;
        this.endDate = endDate;

        this.expenseManager = new ExpensesManager();
        this.postManager = new PostManager();

        this.datePropositionList = new ArrayList<>();
        this.datePropositionList.add(new DateProposition(
                startDate,
                endDate,
                creatorId
        ));
        this.analyzedDatePropositionList = new ArrayList<>();
        this.organizers = new ArrayList<>();
        this.participants = new ArrayList<>();

    }

    public Trip updateDateBasedOnBestMatch(){
        DatePropositionMatcher matcher = new DatePropositionMatcher(this.datePropositionList);
        this.analyzedDatePropositionList = matcher.runAnalysis();
        this.setStartDate(this.getAnalyzedDatePropositionList().get(0).getStartDate());
        this.setEndDate(this.getAnalyzedDatePropositionList().get(0).getEndDate());
        return this;
    }

    public Trip runAnalysis(){
        DatePropositionMatcher matcher = new DatePropositionMatcher(this.datePropositionList);
        this.analyzedDatePropositionList = matcher.runAnalysis();
        return this;
    }

    public DatePropositionReturnedListDto getDateMatcherReport(){

        List<DatePropositionReturnDto> listOfDateProposition = new ArrayList<>();

        for (DateProposition it: this.analyzedDatePropositionList) {
            listOfDateProposition.add(new DatePropositionReturnDto(it.datePropositionToString(), it.getAccuracy()));
        }
        return new DatePropositionReturnedListDto(
                analyzedDatePropositionList.get(0).getStartDate().toString(),
                analyzedDatePropositionList.get(0).getEndDate().toString(),
                listOfDateProposition
        );
    }

    public int findNumberOfAddPropositions(Long ownerId){
        int counter = 0;
        for(DateProposition it: this.datePropositionList){
            if(ownerId.equals(it.getOwnerId())) {
                counter ++;
            }
        }
        return counter;
    }

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
        if(datePropositionList == null){
            datePropositionList = new ArrayList<>();
        }
           dateProposition.setTrip(this);
           datePropositionList.add(dateProposition);
    }

    public boolean removeDateProposition(DateProposition dateProposition) {
        return datePropositionList.remove(dateProposition);
    }

    public Trip updateTripFromTripUpdateDto(TripUpdateDto updatedTripDetails){

        this.tripName = updatedTripDetails.getTripName();
        this.tripDescription = updatedTripDetails.getTripDescription();
        this.startDate = LocalDate.parse(updatedTripDetails.getStartDate());
        this.endDate = LocalDate.parse(updatedTripDetails.getEndDate());
        this.location.updateLocationEntity(updatedTripDetails.getLocation());

        return  this;
    }

    @Override
    public int hashCode() {
        return tripId == null ? 0 : tripId.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if( tripId == null || obj == null || getClass() != obj.getClass())
            return false;

        Trip that = (Trip)  obj;
        return tripId.equals(that.tripId);
     }
}
