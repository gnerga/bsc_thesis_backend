package com.nerga.travelCreatorApp.security.auth.database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nerga.travelCreatorApp.trip.Trip;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class UserEntity {

    private Long id;
    private String username;
    private String password;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> permissions;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLock;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;
    private String firstName;
    private String lastName;
    @Column(unique=true)
    private String email;
    @Column(unique=true)
    private String phoneNumber;

    @JsonIgnore
    @ManyToMany(fetch= FetchType.LAZY)
    @JoinTable(name="user_trups",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name="trip_id"))
    private List<Trip> usersTrips;
    @JsonIgnore
    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name="user_organized_trips",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="trip_id"))
    private List<Trip> organizedTrips;

    public UserEntity(Long id,
                      String username,
                      String password,
                      Set<String> permissions,
                      boolean isAccountNonExpired, boolean isAccountNonLock, boolean isCredentialsNonExpired, boolean isEnabled, String firstName, String lastName, String email, String phoneNumber, List<Trip> usersTrips, List<Trip> organizedTrips) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.permissions = permissions;
        this.isAccountNonExpired = isAccountNonExpired;
        this.isAccountNonLock = isAccountNonLock;
        this.isCredentialsNonExpired = isCredentialsNonExpired;
        this.isEnabled = isEnabled;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.usersTrips = usersTrips;
        this.organizedTrips = organizedTrips;
    }
}
