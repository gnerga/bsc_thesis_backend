package com.nerga.travelCreatorApp.models;

import com.nerga.travelCreatorApp.datepropositionmatcher.DateProposition;
import com.nerga.travelCreatorApp.datepropositionmatcher.DatePropositionMatcher;
import com.nerga.travelCreatorApp.datepropositionmatcher.dto.DatePropositionDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.*;
import org.modelmapper.convention.MatchingStrategies;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DatePropositionMatcherTest {

    DatePropositionMatcher datePropositionMatcher;
    ModelMapper modelMapper;



    @BeforeEach
    public void beforeTest(){
        datePropositionMatcher = new DatePropositionMatcher();
        modelMapper = new ModelMapper();

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        Provider<LocalDate> localDateProvider = new AbstractProvider<LocalDate>() {
            @Override
            public LocalDate get() {
                return LocalDate.now();
            }
        };

        Converter<String, LocalDate> toStringDate = new AbstractConverter<String, LocalDate>() {
            @Override
            protected LocalDate convert(String source) {
                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                return LocalDate.parse(source, format);
            }
        };


        modelMapper.createTypeMap(String.class, LocalDate.class);
        modelMapper.addConverter(toStringDate);
        modelMapper.getTypeMap(String.class, LocalDate.class).setProvider(localDateProvider);

    }

    @Test
    public void addNewDateProposition(){

        datePropositionMatcher.addDateProposition(new DateProposition(LocalDate.parse("2020-10-14"),LocalDate.parse("2020-10-21"), "test_user", 1L));

        assertEquals(LocalDate.parse("2020-10-14"), datePropositionMatcher.getDatePropositionList().get(0).getStartDate());
        assertEquals(LocalDate.parse("2020-10-21"), datePropositionMatcher.getDatePropositionList().get(0).getEndDate());
        assertEquals("test_user", datePropositionMatcher.getDatePropositionList().get(0).getOwnerUsername());
        assertEquals(1L, datePropositionMatcher.getDatePropositionList().get(0).getOwnerId().longValue());

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
        assertEquals(LocalDate.parse(datePropositionDto.getStartDate()), dateProposition.getStartDate());
        assertEquals(LocalDate.parse(datePropositionDto.getEndDate()), dateProposition.getEndDate());
        assertEquals(datePropositionDto.getOwnerUsername(), dateProposition.getOwnerUsername());
        assertEquals(datePropositionDto.getOwnerId(), dateProposition.getOwnerId());

    }

}
