package com.nerga.travelCreatorApp.location;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.HashMap;
import java.util.Map;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long locationId;

    private String locationName;
    private String locationDescription;
    private String googleMapUrl;

//    public Map<String, String> locationToJson(){
//        Map<String, String> entity = new HashMap<>();
//        entity.put("locationId",locationId.toString());
//        entity.put("locationDescription", locationDescription);
//        entity.put("googleMapUrl", googleMapUrl);
//        return entity;
//    }

}
