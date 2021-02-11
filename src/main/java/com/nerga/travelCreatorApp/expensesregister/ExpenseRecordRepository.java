package com.nerga.travelCreatorApp.expensesregister;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRecordRepository extends JpaRepository<ExpenseRecord, Long> {
}
