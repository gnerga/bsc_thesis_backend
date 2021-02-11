package com.nerga.travelCreatorApp.expensesregister;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRecordRepository extends JpaRepository<ExpenseRecord, Long> {
}
