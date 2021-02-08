package com.nerga.travelCreatorApp.expensesregister;

import com.nerga.travelCreatorApp.trip.Trip;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "ExpensesManager")
public class ExpensesManager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long expensesManagerId;

    @OneToMany
    List<Expenses> tripExpenses;

    double overall;

}
