package com.nerga.travelCreatorApp.datepropositionmatcher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class DateProposition implements Comparable<DateProposition> {

    @NonNull
    private LocalDate startDate;
    @NonNull
    private LocalDate endDate;

    private Long leftEdge;
    private Long rightEdge;
    private Long propositionDuration;
    private double accuracy;
    private int numberOfIdenticalProposition = 0;

    public boolean isTheSameObject(DateProposition otherProposition) {
        return this == otherProposition;
    }

    public void increaseNumberOfIdenticalProposition() {
        this.numberOfIdenticalProposition++;
    }

    @Override
    public int compareTo(DateProposition p) {
        if (this.accuracy > p.getAccuracy()) {
            return 1;
        } else if (this.accuracy == p.getAccuracy()) {
            return Integer.compare(
                    this.numberOfIdenticalProposition,
                    p.getNumberOfIdenticalProposition()
            );
        } else {
            return -1;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj==this){
            return true;
        }
        if (obj == null ) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        return (this.startDate.compareTo(((DateProposition)obj).getStartDate()) == 0 && this.endDate.compareTo(((DateProposition)obj).getEndDate()) == 0);
    }

    @Override
    public int hashCode() {
        return this.getStartDate().hashCode() + this.getEndDate().hashCode();
    }
}
