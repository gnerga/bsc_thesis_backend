package com.nerga.travelCreatorApp.dto.trip;

import com.nerga.travelCreatorApp.model.Location;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class TripOutputDto {

    private Long tripId;
    private String tripName;
    private String tripDescription;
    private String locationName;
    private String locationDescription;
    private String googleMapUrl;
    private List<String> users;

    // todo zmienic lobmbokowy konstruktor na własny ... chyba bedzie lepiej

    public static List<String> test(){
        List<String> users = new ArrayList<>();
        for (int i=0;i<5;i++){
            users.add("test");
        }
        return users;
    }

}
