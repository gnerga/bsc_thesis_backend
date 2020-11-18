package com.nerga.travelCreatorApp.security.jwt;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CredentialsAuthenticationRequest {

    private String username;
    private String password;

}
