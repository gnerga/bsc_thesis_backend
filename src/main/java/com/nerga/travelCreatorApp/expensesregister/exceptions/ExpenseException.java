package com.nerga.travelCreatorApp.expensesregister.exceptions;

public abstract class ExpenseException extends RuntimeException {

    public ExpenseException() {}

    public ExpenseException(String message) {
        super(message);
    }

}
