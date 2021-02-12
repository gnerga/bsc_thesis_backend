package com.nerga.travelCreatorApp.datepropositionmatcher.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class DatePropositionReturnDto {

    String datePropositionString;
    double accuracy;

}
