package com.nerga.travelCreatorApp.costallocation;

import com.nerga.travelCreatorApp.security.auth.database.UserEntity;

import java.util.List;

public class CostAllocation {

    private String purposeName;
    private String purposeDescription;
    private List<UserEntity> participants;

    private int numberOfParticipants;
    private float contribution;
    private float overallCost;
    private float costLeft;


}
