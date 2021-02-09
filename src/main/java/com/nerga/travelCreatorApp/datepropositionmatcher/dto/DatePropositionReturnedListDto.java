package com.nerga.travelCreatorApp.datepropositionmatcher.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class DatePropositionReturnedListDto {

    String bestStartDay;
    String bestEndDay;
    List<DatePropositionReturnDto> listOfDatePropositionReturnDto;

}
