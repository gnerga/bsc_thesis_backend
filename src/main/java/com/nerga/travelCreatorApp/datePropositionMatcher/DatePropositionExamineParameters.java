package com.nerga.travelCreatorApp.datePropositionMatcher;


import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class DatePropositionExamineParameters {

    private long examineLength;
    private long testedLength;
    private long examineTestedLengthDifference;
    private long coverageResult;
    private double result;

    @NonNull
    private DateProposition examineProposition;
    @NonNull
    private DateProposition testedProposition;



    public void setAllExamineParameters() {
        this.examineLength = examineProposition.getLeftEdge() + examineProposition.getPropositionDuration();
        this.testedLength = testedProposition.getLeftEdge() + testedProposition.getPropositionDuration();
        this.examineTestedLengthDifference = this.examineLength - this.testedLength;
        this.coverageResult = 0;
    }



}
