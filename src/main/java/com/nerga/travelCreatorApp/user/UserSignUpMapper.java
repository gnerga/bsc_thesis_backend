package com.nerga.travelCreatorApp.user;

import com.nerga.travelCreatorApp.common.Transformer;
import org.springframework.stereotype.Component;

@Component
public class UserSignUpMapper implements Transformer<UserSignUpDto, User> {

    @Override
    public User transform(UserSignUpDto dto) {
        return User.builder()
                .userLogin(dto.getUserLogin())
                .password(dto.getPassword())
                .firstName(dto.getFirstName())
                .lastname(dto.getLastName())
                .email(dto.getEmail())
                .phoneNumber(dto.getPhoneNumber())
                .build();
    }


}
