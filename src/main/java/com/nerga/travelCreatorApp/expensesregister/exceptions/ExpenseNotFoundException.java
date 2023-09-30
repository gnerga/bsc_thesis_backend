package com.nerga.travelCreatorApp.expensesregister.exceptions;

public class ExpenseNotFoundException extends ExpenseException{

    public ExpenseNotFoundException() {
        super();
    }

    public ExpenseNotFoundException(String message) {
        super(message);
    }
}
