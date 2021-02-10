package com.nerga.travelCreatorApp.datepropositionmatcher.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
@ToString
public class DatePropositionReturnedListDto {

    String bestStartDay;
    String bestEndDay;
    List<DatePropositionReturnDto> listOfDatePropositionReturnDto;

}
