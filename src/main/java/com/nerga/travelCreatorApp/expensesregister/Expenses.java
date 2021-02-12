package com.nerga.travelCreatorApp.expensesregister;

import com.nerga.travelCreatorApp.expensesregister.dto.ExpenseUpdateDto;
import com.nerga.travelCreatorApp.security.auth.database.UserEntity;
import com.nerga.travelCreatorApp.trip.Trip;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="expenses")
@NoArgsConstructor
@AllArgsConstructor
public class Expenses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long expensesId;

    String title;
    String description;

    @ManyToOne
    Trip trip;

    float cost;

    @OneToMany
    List<ExpenseRecord> shareholders;

    public Expenses(
            String title,
            String description,
            float cost,
            List<ExpenseRecord> shareholders
    ){
        this.title = title;
        this.description = description;
        this.cost = cost;
        this.shareholders = shareholders;
    }

    public Expenses updateFromExpensesUpdateDto(ExpenseUpdateDto updateDto){
        this.title = validator(this.title, updateDto.getTitle());
        this.description = validator(this.description, updateDto.getDescription());
        return this;
    }

    private String validator(String oldValue, String newValue){
         if (oldValue.equals(newValue)) {
             return oldValue;
         } else {
             return newValue;
         }
    }

    @Override
    public int hashCode() {
        return expensesId == null ? 0 : expensesId.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(this==obj)
            return true;
        if(expensesId == null || obj == null || getClass() != obj.getClass())
            return false;

        Expenses that = (Expenses) obj;
        return expensesId.equals(that.expensesId);
    }

}
