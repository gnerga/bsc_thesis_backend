package com.nerga.travelCreatorApp.dto.trip;

import com.nerga.travelCreatorApp.dto.user.UserDetailsDto;
import com.nerga.travelCreatorApp.model.Location;
import com.nerga.travelCreatorApp.model.User;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class TripOutputDto {

    @NotNull
    private Long tripId;
    @NotNull
    private String tripName;
    @NotNull
    private String tripDescription;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    @NotNull
    private String locationName;
    @NotNull
    private String locationDescription;
    @NotNull
    private String googleMapUrl;

//    private List<User> organizers;
//    private List<User> users;

    private List<UserDetailsDto> organizers;
    private List<UserDetailsDto> members;



}
