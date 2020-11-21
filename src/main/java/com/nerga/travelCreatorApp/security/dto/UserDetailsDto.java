package com.nerga.travelCreatorApp.security.dto;

import com.nerga.travelCreatorApp.security.auth.database.UserEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@Data
public class UserDetailsDto {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

//    public Map<String, String> userDetailsDtoToJsonFormat(){
//        Map<String, String> entity = new HashMap<>();
//        entity.put("username", this.username);
//        entity.put("firstName", this.firstName);
//        entity.put("lastName", this.lastName);
//        entity.put("email", this.email);
//        entity.put("phoneNumber", this.phoneNumber);
//        return entity;
//    }


}
