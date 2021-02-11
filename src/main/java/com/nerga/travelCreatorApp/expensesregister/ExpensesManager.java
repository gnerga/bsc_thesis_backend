package com.nerga.travelCreatorApp.expensesregister;

import com.nerga.travelCreatorApp.expensesregister.dto.ExpenseUpdateDto;
import com.nerga.travelCreatorApp.expensesregister.dto.ExpensesCreateDto;
import com.nerga.travelCreatorApp.trip.Trip;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
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
        expenses.setExpensesManager(this);
    }

    public void removeExpenses(Expenses expenses){
        tripExpenses.remove(expenses);
    }

    public Expenses findExpenses(long expenseId){
        return tripExpenses
                .stream()
                .filter(element -> element.expensesId == expenseId)
                .findFirst().orElse(null);
    }

    public boolean updateExpenses(ExpenseUpdateDto expenseUpdateDto){
        for(Expenses it: this.tripExpenses){
            if(it.expensesId == expenseUpdateDto.getExpenseId()){
                it.updateFromExpensesUpdateDto(expenseUpdateDto);
                return true;
            }
        }
        return false;
    }

}
