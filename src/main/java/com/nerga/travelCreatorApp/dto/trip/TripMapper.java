package com.nerga.travelCreatorApp.dto.trip;

import com.nerga.travelCreatorApp.common.Transformer;
import com.nerga.travelCreatorApp.model.Trip;
import org.springframework.stereotype.Component;

@Component
public class TripMapper implements Transformer<TripDto, Trip> {

    @Override
    public Trip transform(TripDto object) {
        return null;
    }
}
