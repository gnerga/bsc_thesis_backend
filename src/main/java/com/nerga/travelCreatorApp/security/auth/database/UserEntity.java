package com.nerga.travelCreatorApp.security.auth.database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nerga.travelCreatorApp.security.auth.User;
import com.nerga.travelCreatorApp.security.configuration.UserRole;
import com.nerga.travelCreatorApp.security.dto.UserDetailsDto;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@Data
//@Builder(access= AccessLevel.PUBLIC)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    @NonNull
    private String password;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> permissions;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLock;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String phoneNumber;

//    @JsonIgnore
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "user_trips",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "trip_id"))
//    private List<Trip> usersTrips;
//    @JsonIgnore
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "user_organized_trips",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "trip_id"))
//    private List<Trip> organizedTrips;

    public UserEntity(
            String username,
            String password,
            UserRole userRole,
            String email
    ) {
        this.username = username;
        this.password = password;
        this.permissions = getPermissions(userRole.getGrantedAuthority());
        this.isAccountNonExpired = true;
        this.isAccountNonLock = true;
        this.isCredentialsNonExpired = true;
        this.isEnabled = true;
        this.firstName = "n/d";
        this.lastName = "n/d";
        this.email = email;
        this.phoneNumber = "n/d";

//        this.usersTrips = new ArrayList<>();
//        this.organizedTrips = new ArrayList<>();
    }

    public UserEntity(
            String username,
            String password,
            UserRole userRole,
            String firstName,
            String lastName,
            String email,
            String phoneNumber
    ) {
        this.username = username;
        this.password = password;
        this.permissions = getPermissions(userRole.getGrantedAuthority());
        this.isAccountNonExpired = true;
        this.isAccountNonLock = true;
        this.isCredentialsNonExpired = true;
        this.isEnabled = true;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;

//        this.usersTrips = new ArrayList<>();
//        this.organizedTrips = new ArrayList<>();

    }

    public User getUserFromEntity(){
        return new User(
                username, password, getGrantedAuthority(), isAccountNonExpired, isAccountNonLock, isCredentialsNonExpired,
                isEnabled, firstName, lastName, email, phoneNumber
        );
    }

    private Set<String> getPermissions(Collection<? extends GrantedAuthority> grantedAuthority) {
        return grantedAuthority
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
    }

    private Set<SimpleGrantedAuthority> getGrantedAuthority() {
        return permissions.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

    public UserEntity updateUserEntity (UserDetailsDto userDetailsDto) {

        this.firstName = simpleValidatorEmptyInputString(userDetailsDto.getFirstName(), this.firstName);
        this.lastName = simpleValidatorEmptyInputString(userDetailsDto.getLastName(), this.lastName);
        this.email = simpleValidatorEmptyInputString(userDetailsDto.getEmail(), this.email);
        this.phoneNumber = simpleValidatorEmptyInputString(userDetailsDto.getPhoneNumber(), this.phoneNumber);

        return this;
    }

    private String simpleValidatorEmptyInputString(String inputNewValue, String inputOldValue){
        return inputNewValue.isBlank() ? inputOldValue : inputNewValue;
    }


//    public Map<String, String> toIdJson(){
//        Map<String, String> entity = new HashMap<>();
//        entity.put("login", this.username);
//        entity.put("userId", String.valueOf(this.id));
//        return entity;
//    }
//
//    public Map<String, String> toDetailsJson(){
//        Map<String, String> entity = new HashMap<>();
//        entity.put("login", this.username);
//        entity.put("userId", String.valueOf(this.id));
//        entity.put("firstName", this.firstName);
//        entity.put("lastName", this.lastName);
//        entity.put("email", this.email);
//        entity.put("phoneNumber", this.phoneNumber);
//        return entity;
//    }

}
