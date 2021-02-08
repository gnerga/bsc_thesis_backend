package com.nerga.travelCreatorApp.post;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class PostTest {

    @Test
    void testTuple2(){

       List<Long> idList = new ArrayList<>();

       Long a1 = Long.valueOf(1);
       Long a2 = Long.valueOf(2);
       Long a3 = Long.valueOf(3);
       Long a4 = Long.valueOf(4);

       idList.add(a1);
       idList.add(a2);
       idList.add(a3);
       idList.add(a4);

       Long testId = Long.valueOf(3);

       Long testId2 = Long.valueOf(7);

        System.out.println(idList.contains(testId));
        System.out.println(idList.contains(testId2));

    }

}
