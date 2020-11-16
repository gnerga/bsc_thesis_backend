//package com.nerga.travelCreatorApp.location;
//
//import com.nerga.travelCreatorApp.common.Transformer;
//import org.springframework.stereotype.Component;
//
//@Component
//public class LocationMapper implements Transformer<LocationCreateDto, Location> {
//
//    @Override
//    public Location transform(LocationCreateDto dto) {
//        return Location.builder()
//                .locationName(dto.getLocationName())
//                .locationDescription(dto.getLocationDescription())
//                .googleMapUrl(dto.getGoogleMapUrl())
//                .build();
//    }
//}
