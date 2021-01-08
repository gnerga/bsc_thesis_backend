package com.nerga.travelCreatorApp.expensesregister;

import com.nerga.travelCreatorApp.security.auth.database.UserEntity;

import java.util.List;

public class ExpensesRecord {
    long expensesRecordId;
    String title;
    String description;
    float cost;
    int numberOfParticipant;
    List<UserEntity> shareholders;
}
