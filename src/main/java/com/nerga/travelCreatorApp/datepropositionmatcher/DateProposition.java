package com.nerga.travelCreatorApp.datepropositionmatcher;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class DateProposition implements Comparable<DateProposition> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long datePropositionId;

    @ManyToOne
    private DatePropositionMatcher datePropositionMatcher;

    @NonNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @NonNull
    @JsonFormat(pattern = "yyyy-MM-dd")
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
