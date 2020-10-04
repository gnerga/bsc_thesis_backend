package com.nerga.travelCreatorApp.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDetailsDto {

    private Long userId;
    private String userLogin;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

}
