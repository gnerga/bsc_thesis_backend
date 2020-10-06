package com.nerga.travelCreatorApp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.nerga.travelCreatorApp.dto.user.UserDetailsDto;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(access = AccessLevel.PUBLIC)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(unique = true)
    private String userLogin;
    @NotNull
    private String password;

    private String firstName;
    private String lastname;
    @Column(unique = true)
    private String email;
    private String phoneNumber;

//    @JsonManagedReference
    @JsonIgnore
    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name="user_trips",
        joinColumns = @JoinColumn(name="user_id"),
        inverseJoinColumns = @JoinColumn(name="trip_id"))
    private List<Trip> usersTrips;

//    @JsonManagedReference
    @JsonIgnore
    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name="user_organized_trips",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="trip_id"))
    private List<Trip> organizedTrips;

    public UserDetailsDto userToUserDetailsDto(){
        return new UserDetailsDto(
                this.getUserId(),
                this.getUserLogin(),
                this.getFirstName(),
                this.getLastname(),
                this.getEmail(),
                this.getPhoneNumber()
        );
    }

}
