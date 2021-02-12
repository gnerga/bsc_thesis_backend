package com.nerga.travelCreatorApp.datepropositionmatcher.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DatePropositionDto {

    private String startDate;
    private String endDate;
    private String ownerUsername;
    private Long ownerId;

}
