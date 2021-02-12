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
    Long expenseRecordId;

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

    public ExpenseRecord(
            UserEntity userEntity,
            float amount
    ) {
        this.userEntity = userEntity;
        this.amount = amount;
    }

    @Override
    public int hashCode() {
        return expenseRecordId == null ? 0 : expenseRecordId.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(this==obj)
            return true;
        if(expenseRecordId == null || obj == null || getClass() != obj.getClass())
            return false;

        ExpenseRecord that = (ExpenseRecord) obj;
        return expenseRecordId.equals(that.expenseRecordId);
    }

}
