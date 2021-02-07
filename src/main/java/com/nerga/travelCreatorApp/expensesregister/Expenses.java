package com.nerga.travelCreatorApp.expensesregister;

import com.nerga.travelCreatorApp.security.auth.database.UserEntity;

import java.util.List;

public class Expenses {
    long expensesRecordId;
    String title;
    String description;
    float cost;
    int numberOfParticipant;
    List<ExpenseRecord> shareholders;
}
