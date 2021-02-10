package com.nerga.travelCreatorApp.expensesregister;

import com.nerga.travelCreatorApp.security.auth.database.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "expenses_record")
public class ExpenseRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long expenseRecordId;

    @ManyToOne
    Expenses expenses;

    @ManyToOne
    UserEntity  userEntity;

    float amount;

    public ExpenseRecord(){};

    public ExpenseRecord(
                        UserEntity userEntity,
                        float amount,
                        Expenses expenses
                        ) {
        this.userEntity = userEntity;
        this.amount = amount;
        this.expenses = expenses;
     }
}
