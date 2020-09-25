package com.nerga.travelCreatorApp.model;

import java.util.List;

public class User {

    private Long userId;
    private String userLogin;
    private String password;

    private String firstName;
    private String lastname;
    private String email;
    private String phoneNumber;

    private List<Trip> usersTrips;
    private List<Trip> organizedTrips;


}
