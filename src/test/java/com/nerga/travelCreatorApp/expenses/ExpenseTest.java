package com.nerga.travelCreatorApp.expenses;

import com.nerga.travelCreatorApp.expensesregister.ExpenseRecord;
import com.nerga.travelCreatorApp.expensesregister.Expense;
import com.nerga.travelCreatorApp.security.auth.database.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;


public class ExpenseTest {

    ModelMapper modelMapper;

    @BeforeEach
    void setUp(){
        modelMapper = new ModelMapper();
    }

    private Expense getTestExpense(){
        return new Expense(
                "Składka",
                "Zakupy, opłaty dodatkowe",
                120,
                getExpenseRecordList()
        );
    }

    private List<ExpenseRecord> getExpenseRecordList(){
        List<ExpenseRecord> list = new ArrayList<>();
        list.add(new ExpenseRecord(getTestUserEntity(), 50));
        list.add(new ExpenseRecord(getTestUserEntity_2(), 70));
        return list;
    }

    private UserEntity getTestUserEntity(){
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setUsername("test_user");
        userEntity.setPassword("password_1");
        userEntity.setFirstName("Jan");
        userEntity.setLastName("Nowak");
        userEntity.setEmail("test@mail.com");
        userEntity.setPhoneNumber("1234516");
        return userEntity;
    }

    private UserEntity getTestUserEntity_2(){
        UserEntity userEntity = new UserEntity();

        userEntity.setId(2L);
        userEntity.setUsername("test_user_2");
        userEntity.setPassword("password_1");
        userEntity.setFirstName("Dan");
        userEntity.setLastName("Kovalski");
        userEntity.setEmail("test2@mail.com");
        userEntity.setPhoneNumber("2234516");

        return userEntity;
    }

}
