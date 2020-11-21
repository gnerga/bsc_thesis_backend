package com.nerga.travelCreatorApp.security;

import com.nerga.travelCreatorApp.common.response.Response;
import com.nerga.travelCreatorApp.common.response.Success;
import com.nerga.travelCreatorApp.security.auth.database.UserEntity;
import com.nerga.travelCreatorApp.security.auth.database.UserRepository;
import com.nerga.travelCreatorApp.security.configuration.UserRole;
import com.nerga.travelCreatorApp.security.dto.CreateUserDto;
import com.nerga.travelCreatorApp.security.dto.UserCredentialsDto;
import com.nerga.travelCreatorApp.security.dto.UserDetailsDto;
import com.nerga.travelCreatorApp.security.dto.UserIdDto;
import com.nerga.travelCreatorApp.common.response.Error;
import io.vavr.control.Option;
import io.vavr.control.Validation;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Response findUserDetailsById(Long id) {
        return Option.ofOptional(userRepository.findById(id))
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

    public Response updateUserById(Long id, UserDetailsDto userDetailsDto) {
        return Option.ofOptional(userRepository.findById(id))
                .map(userEntity -> userRepository.save(userEntity.updateUserEntity(userDetailsDto)))
                .toEither(Error.badRequest("USER_NOT_FOUND"))
                .fold(Function.identity(), Success::ok);
    }

    public Response updateUserByUsername(String username, UserDetailsDto userDetailsDto) {
        return Option.ofOptional(userRepository.findByUsername(username))
                .map(userEntity -> userRepository.save(userEntity.updateUserEntity(userDetailsDto)))
                .toEither(Error.badRequest("USER_NOT_FOUND"))
                .fold(Function.identity(), Success::ok);
    }

    public Response updateUserPasswordByUsername(String username, UserCredentialsDto userCredentialsDto){
        return Option.ofOptional(userRepository.findByUsername(username))
                .peek(userEntity -> userEntity.setPassword(passwordEncoder.encode(userCredentialsDto.getPassword())))
                .toEither(Error.badRequest("USER_NOT_FOUND"))
                .fold(Function.identity(), Success::ok);
    }

    public Response updateUserPasswordById(Long id, UserCredentialsDto userCredentialsDto){
        return Option.ofOptional(userRepository.findById(id))
                .peek(userEntity -> userEntity.setPassword(passwordEncoder.encode(userCredentialsDto.getPassword())))
                .toEither(Error.badRequest("USER_NOT_FOUND"))
                .fold(Function.identity(), Success::ok);
    }

    public Response deleteUserByUsername(String username){
        return Option.ofOptional(userRepository.findByUsername(username))
                .peek(userRepository::delete)
                .toEither(Error.badRequest("USER_NOT_FOUND"))
                .fold(Function.identity(), Success::ok);
    }

    public Response deleteUserById(Long id){
        return Option.ofOptional(userRepository.findById(id))
                .peek(userRepository::delete)
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

}
