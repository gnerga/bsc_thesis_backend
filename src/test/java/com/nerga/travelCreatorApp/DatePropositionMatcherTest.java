package com.nerga.travelCreatorApp;

import com.nerga.travelCreatorApp.datepropositionmatcher.DateProposition;
import com.nerga.travelCreatorApp.datepropositionmatcher.DatePropositionMatcher;
import com.nerga.travelCreatorApp.datepropositionmatcher.dto.DatePropositionDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;

public class DatePropositionMatcherTest {

    DatePropositionMatcher datePropositionMatcher;
    ModelMapper modelMapper;

    @Before
    public void beforeTest(){
        datePropositionMatcher = new DatePropositionMatcher();
        modelMapper = new ModelMapper();
    }

    @Test
    public void addNewDateProposition(){

        datePropositionMatcher.addDateProposition(new DateProposition(LocalDate.parse("2020-10-14"),LocalDate.parse("2020-10-21"), "test_user", 1L));

        Assert.assertEquals(LocalDate.parse("2020-10-14"), datePropositionMatcher.getDatePropositionList().get(0).getStartDate());
        Assert.assertEquals(LocalDate.parse("2020-10-21"), datePropositionMatcher.getDatePropositionList().get(0).getEndDate());
        Assert.assertEquals("test_user", datePropositionMatcher.getDatePropositionList().get(0).getOwnerLogin());
        Assert.assertEquals(1L, datePropositionMatcher.getDatePropositionList().get(0).getOwnerId().longValue());

    }

    @Test
    public void convertNewDatePropositionBasedOnDatePropositionDto(){
        DatePropositionDto datePropositionDto = new DatePropositionDto(
                "2020-10-14",
                "2020-10-21",
                "test_user",
                1L
        );

        DateProposition dateProposition = modelMapper.map(datePropositionDto, DateProposition.class);

        Assert.assertEquals(LocalDate.parse(datePropositionDto.getStartDate()), dateProposition.getStartDate());
        Assert.assertEquals(LocalDate.parse(datePropositionDto.getEndDate()), dateProposition.getEndDate());
        Assert.assertEquals(datePropositionDto.getLogin(), dateProposition.getOwnerLogin());
        Assert.assertEquals(datePropositionDto.getId(), dateProposition.getOwnerId());

    }

}
