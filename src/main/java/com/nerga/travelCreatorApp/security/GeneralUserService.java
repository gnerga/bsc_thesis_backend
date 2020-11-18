package com.nerga.travelCreatorApp.security;

import com.nerga.travelCreatorApp.security.auth.database.UserEntity;
import com.nerga.travelCreatorApp.security.auth.database.UserRepository;
import com.nerga.travelCreatorApp.security.configuration.UserRole;
import com.nerga.travelCreatorApp.security.dto.CreateUserDto;
import io.vavr.control.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class GeneralUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public GeneralUserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
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

    public Map<String, String> getUserByUsername(String username) {

        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() -> new
                UsernameNotFoundException("PROVIDED_USERNAME_DOSE_NOT_EXIST"));
        return userEntity.toJson();

    }

    public Map<String, String> getUserDetailsByUsername(String username){
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() -> new
                UsernameNotFoundException("PROVIDED_USERNAME_DOSE_NOT_EXIST"));
        return userEntity.detailsToJson();
    }

    private Optional<UserEntity> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }



}
