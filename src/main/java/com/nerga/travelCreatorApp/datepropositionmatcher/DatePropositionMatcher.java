package com.nerga.travelCreatorApp.datepropositionmatcher;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nerga.travelCreatorApp.datepropositionmatcher.dto.DatePropositionDto;
import com.nerga.travelCreatorApp.datepropositionmatcher.dto.DatePropositionReturnDto;
import com.nerga.travelCreatorApp.datepropositionmatcher.dto.DatePropositionReturnedListDto;
import com.nerga.travelCreatorApp.trip.Trip;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor


public class DatePropositionMatcher {

    private List<DateProposition> datePropositionList;

    private List<DateProposition> analyzedDatePropositionList;


    public DatePropositionMatcher(
    ){
        this.datePropositionList = new ArrayList<>();
        this.analyzedDatePropositionList = new ArrayList<>();
    }

    public DatePropositionMatcher(
            List<DateProposition> datePropositionList
    ){
        this.datePropositionList = datePropositionList;
        this.analyzedDatePropositionList = new ArrayList<>();
    }

    public List<DateProposition> runAnalysis(){
        analyze();
        return sortBtAccuracy();
    }

    public List<DateProposition> analyze(){
        examineAccuracy();
        return removeDuplicatedPropositions();
    }

    public List<DateProposition> sortBtAccuracy(){
        return this.analyzedDatePropositionList.stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
    }

    private  List<DateProposition> removeDuplicatedPropositions(){
        this.analyzedDatePropositionList = this.datePropositionList.stream().distinct().collect(Collectors.toList());
        return this.analyzedDatePropositionList;
    }

    private void examineAccuracy(){
        setStartAndEndDateThenSetDurationOfProposition();
        DatePropositionSet datePropositionSet =
                new DatePropositionSet(collectAllDatesFromPropositionsIntoOneCollection());
        setPropositionLeftAndRightEdge(datePropositionSet);
        countAccuracy();
    }

    private void countAccuracy(){

        double sumOfResult;
        boolean isLeftInRange;
        boolean isRightInRange;

        for (DateProposition examineProposition: this.datePropositionList) {

            sumOfResult = 0;

            for (DateProposition testedProposition: this.datePropositionList){
                if(examineProposition.isTheSameObject(testedProposition)){
                    continue;
                }

                isLeftInRange = isLeftEdgeIsInCoverage(examineProposition, testedProposition);
                isRightInRange = isRightEdgeIsInCoverage(examineProposition, testedProposition);

                if (isLeftInRange && isRightInRange) {
                    sumOfResult += compareRanges(examineProposition, testedProposition);
                }
            }
            examineProposition.setAccuracy(sumOfResult);
        }
    }


    private double compareRanges(
            DateProposition examineProposition,
            DateProposition testedProposition
    ){
        DatePropositionExamineParameters examination
                = new DatePropositionExamineParameters(
                examineProposition,
                testedProposition
        );

        examination.setAllExamineParameters();

        ifExaminationTestedLengthDifferenceIsGraterThenZero(examination,examineProposition);
        ifExaminationTestedLengthDifferenceIsEqualsZero(examination,examineProposition, testedProposition);
        ifExaminationTestedLengthDifferenceIsSmallerThenZero(examination,examineProposition, testedProposition);


        return examination.getResult();
    }

    private void ifExaminationTestedLengthDifferenceIsGraterThenZero(
            DatePropositionExamineParameters examination,
            DateProposition examineProposition
                                                                     ){
        if (examination.getExamineTestedLengthDifference() > 0) {

            examination.setCoverageResult(
                    examineProposition.getPropositionDuration() -
                            examination.getExamineTestedLengthDifference());

            examination.setResult(
                    (double)examination.getCoverageResult() /
                            examineProposition.getPropositionDuration());

        }
    }

    private void ifExaminationTestedLengthDifferenceIsEqualsZero(
            DatePropositionExamineParameters examination,
            DateProposition examineProposition,
            DateProposition testedProposition
    ){
        if (examination.getExamineTestedLengthDifference() == 0) {
            if(examineProposition.getPropositionDuration().equals(testedProposition.getPropositionDuration())) {
                examineProposition.increaseNumberOfIdenticalProposition();
                examination.setResult(1.0);
            } else if (examineProposition.getPropositionDuration() < testedProposition.getPropositionDuration()) {
                examination.setResult(1.0);
            } else {
                examination.setCoverageResult(
                        examineProposition.getPropositionDuration() -
                                testedProposition.getPropositionDuration());
                examination.setResult(
                        (double)examination.getCoverageResult() /
                                examineProposition.getPropositionDuration());
            }
        }
    }

    private void ifExaminationTestedLengthDifferenceIsSmallerThenZero(
            DatePropositionExamineParameters examination,
            DateProposition examineProposition,
            DateProposition testedProposition
    ){
        if (examination.getExamineTestedLengthDifference() < 0){
            examination.setCoverageResult(
                    testedProposition.getPropositionDuration() +
                            examination.getExamineTestedLengthDifference());
            examination.setResult(
                    (double)examination.getCoverageResult() /
                            examineProposition.getPropositionDuration());
        }
    }

    private void setPropositionLeftAndRightEdge(DatePropositionSet datePropositionSet){
        this.datePropositionList.forEach(dateProposition -> {
            dateProposition.setLeftEdge(ChronoUnit.DAYS.between(datePropositionSet.getMin(), dateProposition.getStartDate()));
            dateProposition.setRightEdge(Math.abs(ChronoUnit.DAYS.between(datePropositionSet.getMin(), dateProposition.getEndDate())));
        });
    }

    private boolean isLeftEdgeIsInCoverage(DateProposition examineProposition, DateProposition testedProposition){
        long leftEdgeCoverage = examineProposition.getLeftEdge() - testedProposition.getPropositionDuration();

        return leftEdgeCoverage < testedProposition.getLeftEdge() &&
                testedProposition.getLeftEdge() < examineProposition.getRightEdge();
    }

    private void setStartAndEndDateThenSetDurationOfProposition(){
        this.datePropositionList
                .forEach(dateProposition -> {
                    Long days = ChronoUnit.DAYS.between(
                            dateProposition.getStartDate(),
                            dateProposition.getEndDate());
                    dateProposition.setPropositionDuration(days);
                });
    }

    private List<LocalDate> collectAllDatesFromPropositionsIntoOneCollection(){
        List<LocalDate> allDatePropositions = new ArrayList<>();
        this.datePropositionList.forEach(dateProposition -> {
            allDatePropositions.add(dateProposition.getStartDate());
            allDatePropositions.add(dateProposition.getEndDate());
        });
        return allDatePropositions;
    }

    private boolean isRightEdgeIsInCoverage(DateProposition examineProposition, DateProposition testedProposition){
        long rightEdgeCoverage = examineProposition.getRightEdge() + testedProposition.getPropositionDuration();

        return rightEdgeCoverage > testedProposition.getRightEdge() &&
                testedProposition.getRightEdge() > examineProposition.getLeftEdge();
    }


}