package com.nerga.travelCreatorApp.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(access = AccessLevel.PUBLIC)
public class User {

    @Id
    private Long userId;
    private String userLogin;
    private String password;

    private String firstName;
    private String lastname;
    private String email;
    private String phoneNumber;

//    @ManyToMany
//    private List<Trip> usersTrips;
//    @ManyToMany
//    private List<Trip> organizedTrips;


}
