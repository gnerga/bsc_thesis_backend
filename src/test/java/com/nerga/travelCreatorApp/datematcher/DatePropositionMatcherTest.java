package com.nerga.travelCreatorApp.datematcher;

import com.nerga.travelCreatorApp.datepropositionmatcher.DateProposition;
import com.nerga.travelCreatorApp.datepropositionmatcher.DatePropositionMatcher;
import com.nerga.travelCreatorApp.datepropositionmatcher.dto.DatePropositionDto;
import com.nerga.travelCreatorApp.datepropositionmatcher.dto.DatePropositionReturnDto;
import com.nerga.travelCreatorApp.datepropositionmatcher.dto.DatePropositionReturnedListDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.*;
import org.modelmapper.convention.MatchingStrategies;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DatePropositionMatcherTest {

    DatePropositionMatcher datePropositionMatcher;
    ModelMapper modelMapper;



    @BeforeEach
    public void beforeTest(){
        datePropositionMatcher = new DatePropositionMatcher(7, LocalDate.parse("2020-10-14"));
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

    @Test
    public void shouldReturnStringWithStartAndEndDate(){

        String testText = "From: "
                + "14" + ' '
                + "OCTOBER" + ' '
                + "2020"+
                '\n'
                + " To: "
                + "21" + ' '
                + "OCTOBER" + ' '
                + "2020";

        DateProposition proposition =
                new DateProposition(
                        LocalDate.parse("2020-10-14"),
                        LocalDate.parse("2020-10-21"),
                        "test_user", 1L);

        assertEquals(proposition.datePropositionToString(), testText);
    }

    @Test
    public void shouldHowManyPropositionUserSend(){

        int expectedNumber = 3;
        Long userId = 1L;

        DateProposition proposition1 =
                new DateProposition(
                        LocalDate.parse("2020-10-14"),
                        LocalDate.parse("2020-10-21"),
                        "test_user", 1L);

        DateProposition proposition2 =
                new DateProposition(
                        LocalDate.parse("2020-10-21"),
                        LocalDate.parse("2020-10-28"),
                        "test_user", 1L);

        DateProposition proposition3 =
                new DateProposition(
                        LocalDate.parse("2020-10-07"),
                        LocalDate.parse("2020-10-14"),
                        "test_user", 1L);


        datePropositionMatcher.addDateProposition(proposition1);
        datePropositionMatcher.addDateProposition(proposition2);
        datePropositionMatcher.addDateProposition(proposition3);

        int result = datePropositionMatcher.findNumberOfAddPropositions(userId);

        assertEquals(expectedNumber, result);

    }

    @Test
    public void shouldReturnDateMatcherReport(){

        DateProposition proposition1 =
                new DateProposition(
                        LocalDate.parse("2020-10-16"),
                        LocalDate.parse("2020-10-22"),
                        "test_user", 1L);

        DateProposition proposition2 =
                new DateProposition(
                        LocalDate.parse("2020-10-18"),
                        LocalDate.parse("2020-10-25"),
                        "test_user", 1L);

        DateProposition proposition3 =
                new DateProposition(
                        LocalDate.parse("2020-10-10"),
                        LocalDate.parse("2020-10-17"),
                        "test_user", 1L);

        List<DatePropositionReturnDto> list = new ArrayList<DatePropositionReturnDto>();
        list.add(new DatePropositionReturnDto("From: 16 OCTOBER 2020 To: 22 OCTOBER 2020", 0.8333333333333333));
        list.add(new DatePropositionReturnDto("From: 18 OCTOBER 2020 To: 25 OCTOBER 2020", 0.5714285714285714));
        list.add(new DatePropositionReturnDto("From: 10 OCTOBER 2020 To: 17 OCTOBER 2020",0.14285714285714285));


        DatePropositionReturnedListDto expected = new DatePropositionReturnedListDto(
                "2020-10-16",
                "2020-10-22",
                list
        );

        datePropositionMatcher.addDateProposition(proposition1);
        datePropositionMatcher.addDateProposition(proposition2);
        datePropositionMatcher.addDateProposition(proposition3);
        datePropositionMatcher.runAnalysis();

        DatePropositionReturnedListDto result = datePropositionMatcher.getDateMatcherReport();

        assertEquals(expected.getBestStartDay(), result.getBestStartDay());
        assertEquals(expected.getBestEndDay(), result.getBestEndDay());
        assertEquals(expected.getListOfDatePropositionReturnDto().size(), result.getListOfDatePropositionReturnDto().size());

    }

}
