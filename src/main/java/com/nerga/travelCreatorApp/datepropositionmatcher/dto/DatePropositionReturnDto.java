package com.nerga.travelCreatorApp.datepropositionmatcher.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DatePropositionReturnDto {
    String datePropositionString;
    double accuracy;
}
