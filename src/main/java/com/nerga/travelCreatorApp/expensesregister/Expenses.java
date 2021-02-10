package com.nerga.travelCreatorApp.expensesregister;

import com.nerga.travelCreatorApp.security.auth.database.UserEntity;
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
    long expensesId;

    String title;
    String description;

    @ManyToOne
    ExpensesManager expensesManager;

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



}
