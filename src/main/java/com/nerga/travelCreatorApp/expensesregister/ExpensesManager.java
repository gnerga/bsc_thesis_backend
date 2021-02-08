package com.nerga.travelCreatorApp.expensesregister;

import com.nerga.travelCreatorApp.trip.Trip;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
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

    public ExpensesManager(){
        tripExpenses = new ArrayList<>();
    }

    public void addExpenses(Expenses expenses){
        tripExpenses.add(expenses);
    }

    public void removeExpenses(Expenses expenses){
        tripExpenses.remove(expenses);
    }

    public Expenses findExpenses(int expenseId){
        return tripExpenses
                .stream()
                .filter(element -> element.expensesId == expenseId)
                .findFirst().orElse(null);
    }

}
