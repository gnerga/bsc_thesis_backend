package com.nerga.travelCreatorApp.security.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nerga.travelCreatorApp.trip.Trip;
import com.nerga.travelCreatorApp.user.UserDetailsDto;
import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;

//@Entity
@Data
@Builder(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(unique = true)
    private String username;
    @NotNull
    private String password;
    private Set<?extends GrantedAuthority> grantedAuthorities;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLock;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;

    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
//    @Column(unique = true)
    private String phoneNumber;

    @JsonIgnore
    @ManyToMany(fetch=FetchType.LAZY)
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.grantedAuthorities;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    public UserDetailsDto userToUserDetailsDto(){
        return new UserDetailsDto(
                this.getUserId(),
                this.getUsername(),
                this.getFirstName(),
                this.getLastName(),
                this.getEmail(),
                this.getPhoneNumber()
//                ,this.isAccountNonExpired = true,
//                this.isAccountNonLock = true,
//                this.isCredentialsNonExpired = true,
//                this.isEnabled = true
        );
    }
}
