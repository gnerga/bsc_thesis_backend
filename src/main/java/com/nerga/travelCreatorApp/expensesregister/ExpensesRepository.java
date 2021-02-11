package com.nerga.travelCreatorApp.expensesregister;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpensesRepository extends JpaRepository<Expenses, Long> {
}
