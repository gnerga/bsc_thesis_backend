package com.nerga.travelCreatorApp.trip;

import com.nerga.travelCreatorApp.common.Transformer;
import org.springframework.stereotype.Component;

@Component
public class TripMapper implements Transformer<TripCreateDto, Trip> {

    @Override
    public Trip transform(TripCreateDto object) {
        return new Trip(

        );
    }
}
