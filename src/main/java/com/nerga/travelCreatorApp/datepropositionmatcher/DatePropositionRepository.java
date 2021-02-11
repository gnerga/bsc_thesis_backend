package com.nerga.travelCreatorApp.datepropositionmatcher;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DatePropositionRepository extends JpaRepository<DateProposition, Long> {
}
