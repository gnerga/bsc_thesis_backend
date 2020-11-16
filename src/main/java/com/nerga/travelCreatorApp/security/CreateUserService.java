package com.nerga.travelCreatorApp.security;

import com.nerga.travelCreatorApp.security.auth.database.UserEntity;
import com.nerga.travelCreatorApp.security.auth.database.UserRepository;
import com.nerga.travelCreatorApp.security.configuration.UserRole;
import com.nerga.travelCreatorApp.security.dto.CreateUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CreateUserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity createUser(CreateUserDto createUserDto){
        return registerUserAccount(createUserDto, UserRole.USER);
    }

    private UserEntity registerUserAccount(CreateUserDto createUserDto, UserRole userRole) {
        String encodePassword = passwordEncoder.encode(createUserDto.getPassword());
        UserEntity userEntity = new UserEntity(
                createUserDto.getUsername(),
                encodePassword,
                userRole,
                createUserDto.getFirstName(),
                createUserDto.getLastName(),
                createUserDto.getEmail(),
                createUserDto.getPhoneNumber()
        );
        return userRepository.save(userEntity);
    }

}
