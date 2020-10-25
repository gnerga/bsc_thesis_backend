package com.nerga.travelCreatorApp.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserSignInDto {

    private String login;
    private String password;

}
