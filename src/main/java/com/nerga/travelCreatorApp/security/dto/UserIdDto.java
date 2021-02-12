package com.nerga.travelCreatorApp.security.dto;

import com.nerga.travelCreatorApp.security.auth.database.UserEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserIdDto {

    private String username;
    private Long id;

}
