package com.nerga.travelCreatorApp.dto.trip;

import com.nerga.travelCreatorApp.common.Transformer;
import com.nerga.travelCreatorApp.model.Trip;
import org.springframework.stereotype.Component;

@Component
public class TripMapper implements Transformer<TripCreateDto, Trip> {

    @Override
    public Trip transform(TripCreateDto object) {
        return new Trip(

        );
    }
}
