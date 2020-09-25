package com.nerga.travelCreatorApp.model;

import java.time.LocalDate;
import java.util.List;

public class Trip {

    private Long tripId;

    private String tripName;
    private Location location;
    private String tripDescription;

    private LocalDate startDate;
    private LocalDate endDate;

    private List<User> organizers;
    private List<User> members;

}
