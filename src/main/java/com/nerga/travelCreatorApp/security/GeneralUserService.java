package com.nerga.travelCreatorApp.security;

import com.nerga.travelCreatorApp.common.response.Response;
import com.nerga.travelCreatorApp.common.response.Success;
import com.nerga.travelCreatorApp.security.auth.database.UserEntity;
import com.nerga.travelCreatorApp.security.auth.database.UserRepository;
import com.nerga.travelCreatorApp.security.configuration.UserRole;
import com.nerga.travelCreatorApp.security.dto.CreateUserDto;
import com.nerga.travelCreatorApp.security.dto.UserDetailsDto;
import com.nerga.travelCreatorApp.security.dto.UserIdDto;
import com.nerga.travelCreatorApp.common.response.Error;
import io.vavr.control.Option;
import io.vavr.control.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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

    public Response createUser (CreateUserDto createUserDto){
        return canRegister(createUserDto)
                .map(user -> registerUserAccount(createUserDto, UserRole.USER))
                .fold(Function.identity(), Success::accepted);
    }

    public Response findUserDetailsByUsername(String username) {
        return Option.ofOptional(userRepository.findByUsername(username))
                .map(UserDetailsDto::new)
                .toEither(Error.badRequest("USER_NOT_FOUND"))
                .fold(Function.identity(), Success::ok);
    }

    public Response findUserIdByUsername(String username) {
        return Option.ofOptional(userRepository.findByUsername(username))
                .map(UserIdDto::new)
                .toEither(Error.badRequest("USER_NOT_FOUND"))
                .fold(Function.identity(), Success::ok);
    }

    public Response tryUpdateUserById(Long id, UserDetailsDto userDetailsDto) {

        return Option.ofOptional(userRepository.findById(id))
                .map(userEntity -> userRepository.save(userEntity.updateUserEntity(userDetailsDto)))
                .toEither(Error.badRequest("USER_NOT_FOUND"))
                .fold(Function.identity(), Success::ok);
    }

    public Response tryUpdateUserByUserName(String username, UserDetailsDto userDetailsDto) {
        return Option.ofOptional(userRepository.findByUsername(username))
                .map(userEntity -> userRepository.save(userEntity.updateUserEntity(userDetailsDto)))
                .toEither(Error.badRequest("USER_NOT_FOUND"))
                .fold(Function.identity(), Success::ok);
    }

    private Validation<Error, CreateUserDto> canRegister(CreateUserDto createUserDto) {
        return !userRepository.existsByUsername(createUserDto.getUsername()) ? Validation.valid(createUserDto)
                : Validation.invalid(Error.badRequest("USERNAME_ALREADY_IN_USE"));
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

    private UserDetailsDto updateUserById(Long id, UserDetailsDto userDetailsDto) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new
                UsernameNotFoundException("PROVIDED_USERNAME_ID_DOES_NOT_EXIST"));

        UserEntity updatedUserEntity = userRepository.save(userEntity.updateUserEntity(userDetailsDto));
        System.out.println(updatedUserEntity);

        return new UserDetailsDto(updatedUserEntity);
    }

    public Response tryChangePasswordByUserId(Long id, String newPassword) {
        return Option.ofOptional(userRepository.findById(id))
                .peek(userEntity -> userEntity.setPassword(passwordEncoder.encode(newPassword)))
                .map(userRepository::save)
                .toEither(Error.badRequest("USER_NOT_FOUND"))
                .fold(Function.identity(), Success::ok);
    }

    public Response tryChangePasswordByUsername(String username, String newPassword){
        return Option.ofOptional(userRepository.findByUsername(username))
                .peek(userEntity -> userEntity.setPassword(passwordEncoder.encode(newPassword)))
                .map(userRepository::save)
                .toEither(Error.badRequest("USER_NOT_FOUND"))
                .fold(Function.identity(), Success::ok);
    }

//    private UserIdDto deleteUserByUserName(String username){
//        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(
//                () -> new UsernameNotFoundException("PROVIDED_USERNAME_DOSE_NOT_EXIST")
//        );
//        UserIdDto userIdDto = new UserIdDto(userEntity);
//        userRepository.delete(userEntity);
//        return userIdDto;
//    }
//
//    private UserIdDto getUserIdByUsername(String username) {
//        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() -> new
//                UsernameNotFoundException("PROVIDED_USERNAME_DOSE_NOT_EXIST"));
//        return new UserIdDto(userEntity);
//    }
//
//    private UserDetailsDto getUserDetailsByUserName(String username) {
//        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() -> new
//                UsernameNotFoundException("PROVIDED_USERNAME_DOSE_NOT_EXIST"));
//        return new UserDetailsDto(userEntity);
//    }
//
//    private Validation<Error, Long> isIdExists(Long id){
//        return userRepository.existsById(id) ? Validation.valid(id)
//                : Validation.invalid(Error.badRequest("USER_NOT_FOUND"));
//    }
//
//    private Validation<Error, String> isUserExist(String username){
//        return userRepository.existsByUsername(username) ? Validation.valid(username)
//                : Validation.invalid(Error.badRequest("USER_NOT_FOUND"));
//    }

}
