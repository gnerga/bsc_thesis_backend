package com.nerga.travelCreatorApp.datepropositionmatcher;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class DatePropositionCollection {

    List<LocalDate> allDatePropositions;
    LocalDate min;
    LocalDate max;

    public DatePropositionCollection (List<LocalDate> allDatePropositions) {
        this.allDatePropositions = allDatePropositions;
        this.setMinAndMaxValue();
    }

    private void setMinAndMaxValue() {
       this.min = allDatePropositions.stream()
                .min(LocalDate::compareTo).get();
        this.max = allDatePropositions.stream()
                .max(LocalDate::compareTo).get();
    }

}
