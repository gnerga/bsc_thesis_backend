package com.nerga.travelCreatorApp.security;

import com.nerga.travelCreatorApp.common.response.Response;
import com.nerga.travelCreatorApp.common.response.Success;
import com.nerga.travelCreatorApp.security.auth.database.UserEntity;
import com.nerga.travelCreatorApp.security.auth.database.UserRepository;
import com.nerga.travelCreatorApp.security.configuration.UserRole;
import com.nerga.travelCreatorApp.security.dto.CreateUserDto;
import com.nerga.travelCreatorApp.security.dto.UserIdDto;
import com.nerga.travelCreatorApp.common.response.Error;
import io.vavr.control.Either;
import io.vavr.control.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
public class GeneralUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public GeneralUserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

//    public UserEntity createUser(CreateUserDto createUserDto){
//        return registerUserAccount(createUserDto, UserRole.USER);
//    }

    public Response createUser (CreateUserDto createUserDto){
        return canRegister(createUserDto).map(user -> registerUserAccount(createUserDto, UserRole.USER)).fold(Function.identity(), Success::accepted);
    }

//    public Map<String, String> getUserByUsername(String username) {
    public UserIdDto getUserByUsername(String username) {
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() -> new
                UsernameNotFoundException("PROVIDED_USERNAME_DOSE_NOT_EXIST"));
        return new UserIdDto(userEntity);
    }

    public Map<String, String> getUserDetailsByUsername(String username){
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() -> new
                UsernameNotFoundException("PROVIDED_USERNAME_DOSE_NOT_EXIST"));
        return userEntity.toDetailsJson();
    }

    private Optional<UserEntity> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    private UserIdDto registerUserAccount(CreateUserDto createUserDto, UserRole userRole) {
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
        return new UserIdDto(userRepository.save(userEntity));
    }

    private Validation<Error, CreateUserDto> canRegister(CreateUserDto createUserDto) {
        return !userRepository.existsByUsername(createUserDto.getUsername()) ? Validation.valid(createUserDto)
                : Validation.invalid(Error.badRequest("USERNAME_ALREADY_IN_USE"));
    }

}
