package com.nerga.travelCreatorApp.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsDto {

    private Long userId;
    private String userLogin;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
}
