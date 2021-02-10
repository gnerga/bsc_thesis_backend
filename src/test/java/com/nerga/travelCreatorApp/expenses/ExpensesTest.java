package com.nerga.travelCreatorApp.expenses;

import com.nerga.travelCreatorApp.expensesregister.ExpenseRecord;
import com.nerga.travelCreatorApp.expensesregister.Expenses;
import com.nerga.travelCreatorApp.expensesregister.ExpensesManager;
import com.nerga.travelCreatorApp.expensesregister.dto.ExpenseRecordCreateDto;
import com.nerga.travelCreatorApp.expensesregister.dto.ExpensesCreateDto;
import com.nerga.travelCreatorApp.security.auth.database.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ExpensesTest {

    ModelMapper modelMapper;
    ExpensesManager expensesManager;

    @BeforeEach
    void setUp(){
        modelMapper = new ModelMapper();
    }

    @Test
    void shouldCreateExpenseManagerWithEmptyListOfExpenses(){
        expensesManager = new ExpensesManager();
        assertNotNull(expensesManager);
        assertNotNull(expensesManager.getTripExpenses());
    }

    @Test
    void shouldAddExpenseToManagerCollection(){
        expensesManager = new ExpensesManager();
        Expenses expenses = getTestExpense();
        expensesManager.addExpenses(expenses);
        assertEquals(expenses, expensesManager.getTripExpenses().get(0));
    }

    @Test
    void shouldRemoveExpenseFromManagerCollection(){
        expensesManager = new ExpensesManager();
        Expenses expenses = getTestExpense();
        expensesManager.addExpenses(expenses);
        expensesManager.removeExpenses(expenses);
        assertEquals(0, expensesManager.getTripExpenses().size());
    }

    @Test
    void shouldFindExpenseWithGivenIdInManagerCollections(){
        expensesManager = new ExpensesManager();
        Expenses expenses = getTestExpense();
        expenses.setExpensesId(1L);
        expensesManager.addExpenses(expenses);
        Expenses found = expensesManager.findExpenses(1L);
        assertEquals(expenses, found);
    }

    private Expenses getTestExpense(){
        return new Expenses(
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
