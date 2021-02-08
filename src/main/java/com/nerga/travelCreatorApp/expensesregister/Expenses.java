package com.nerga.travelCreatorApp.expensesregister;

import com.nerga.travelCreatorApp.security.auth.database.UserEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name="expenses")
public class Expenses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long expensesRecordId;

    String title;
    String description;

    @ManyToOne
    ExpensesManager expensesManager;

    float cost;

    @OneToMany
    List<ExpenseRecord> shareholders;
}
