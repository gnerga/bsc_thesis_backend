package com.nerga.travelCreatorApp.security;

import com.nerga.travelCreatorApp.common.response.Response;
import com.nerga.travelCreatorApp.common.response.Success;
import com.nerga.travelCreatorApp.security.auth.database.UserEntity;
import com.nerga.travelCreatorApp.security.auth.database.UserRepository;
import com.nerga.travelCreatorApp.security.auth.exceptions.CustomUserNotFoundException;
import com.nerga.travelCreatorApp.security.configuration.UserRole;
import com.nerga.travelCreatorApp.security.dto.CreateUserDto;
import com.nerga.travelCreatorApp.security.dto.UserCredentialsDto;
import com.nerga.travelCreatorApp.security.dto.UserDetailsDto;
import com.nerga.travelCreatorApp.security.dto.UserIdDto;
import com.nerga.travelCreatorApp.common.response.Error;
import io.vavr.control.Option;
import io.vavr.control.Validation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class GeneralUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private ModelMapper modelMapper;

    @Autowired
    public GeneralUserService(UserRepository userRepository,
                              PasswordEncoder passwordEncoder,
                              ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    public Response createUser (CreateUserDto createUserDto){
        return canRegister(createUserDto)
                .map(user -> registerUserAccount(createUserDto, UserRole.USER))
                .fold(Function.identity(), Success::created);
    }

    public Response findUserDetailsByUsername(String username) {
        return Option.ofOptional(userRepository.findByUsername(username))
                .map(userEntity -> modelMapper.map(userEntity, UserDetailsDto.class))
                .toEither(Error.badRequest("USER_NOT_FOUND"))
                .fold(Function.identity(), Success::ok);
    }

    public Response findAllUsers(){
        List<UserDetailsDto> userDetailsDtoList =
                returnListOfUserDetailsDto(userRepository.findAll());
        return !userDetailsDtoList.isEmpty() ? Success.ok(userDetailsDtoList) : Error.badRequest("USERS_NOT_FOUND");
    }

    public Response findAllUsersWithoutAuthorizedUser(){
        List<UserDetailsDto> userDetailsDtoList =
                returnListOfUserDetailsDto(userRepository.findAllByUsernameIsNot(
                        SecurityContextHolder.getContext()
                                .getAuthentication()
                                .getPrincipal()
                                .toString()));
        return !userDetailsDtoList.isEmpty() ? Success.ok(userDetailsDtoList) : Error.badRequest("USERS_NOT_FOUND");
    }

    public Response findAllUsersWithoutAuthorizedUserAndContainsGivenText(String text){
        List<UserDetailsDto> userDetailsDtoList;
        List<UserDetailsDto> userDetailsDtoList2;
        List<UserDetailsDto> userDetailsDtoList3;
                userDetailsDtoList = returnListOfUserDetailsDto(userRepository.findAllByUsernameIsNotAndUsernameContains(
                        SecurityContextHolder.getContext()
                                .getAuthentication()
                                .getPrincipal()
                                .toString(), text));
        userDetailsDtoList2 = returnListOfUserDetailsDto(userRepository.findAllByUsernameIsNotAndFirstNameContains(
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getPrincipal()
                        .toString(), text));
        userDetailsDtoList3 = returnListOfUserDetailsDto(userRepository.findAllByUsernameIsNotAndLastNameContains(
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getPrincipal()
                        .toString(), text));
        List<UserDetailsDto> temp= Stream.concat(
                userDetailsDtoList.stream(),
                userDetailsDtoList2.stream()
                ).collect(Collectors.toList());
        List<UserDetailsDto> result = Stream.concat(userDetailsDtoList3.stream(), temp.stream()).collect(Collectors.toList());
        return !result.isEmpty() ? Success.ok(userDetailsDtoList) : Error.badRequest("USERS_NOT_FOUND");
    }

    public Response getUserDetailsForLoggedUser(){
        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal()
                .toString();
        UserEntity user;
        try{
            user = Option.ofOptional(userRepository.findByUsername(username))
                    .getOrElseThrow(() -> new CustomUserNotFoundException("USER_NOt_FOUND"));
        } catch (Exception e){
            return Error.badRequest("USER_NOT_FOUND");
        }
        return Success.ok(modelMapper.map(user, UserDetailsDto.class));

    }

    public Response findUserDetailsById(Long id) {
        return Option.ofOptional(userRepository.findById(id))
                .map(userEntity -> modelMapper.map(userEntity, UserDetailsDto.class))
                .toEither(Error.badRequest("USER_NOT_FOUND"))
                .fold(Function.identity(), Success::ok);
    }

    public Response findUserIdByUsername(String username) {
        return Option.ofOptional(userRepository.findByUsername(username))
                .map(userEntity -> modelMapper.map(userEntity, UserIdDto.class))
                .toEither(Error.badRequest("USER_NOT_FOUND"))
                .fold(Function.identity(), Success::ok);
    }

    public Response updateUserById(Long id, UserDetailsDto userDetailsDto) {
        return Option.ofOptional(userRepository.findById(id))
                .map(userEntity -> userRepository.save(updateUserEntity(userDetailsDto, userEntity)))
                .map(userEntity -> modelMapper.map(userEntity, UserDetailsDto.class))
                .toEither(Error.badRequest("USER_NOT_FOUND"))
                .fold(Function.identity(), Success::ok);
    }

    public Response updateUserByUsername(String username, UserDetailsDto userDetailsDto) {
        return Option.ofOptional(userRepository.findByUsername(username))
                .map(userEntity -> userRepository.save(updateUserEntity(userDetailsDto, userEntity)))
                .map(userEntity -> modelMapper.map(userEntity, UserDetailsDto.class))
                .toEither(Error.badRequest("USER_NOT_FOUND"))
                .fold(Function.identity(), Success::ok);
    }

    public Response updateUserPasswordByUsername(String username, UserCredentialsDto userCredentialsDto){
        return Option.ofOptional(userRepository.findByUsername(username))
                .peek(userEntity -> userEntity.setPassword(passwordEncoder.encode(userCredentialsDto.getPassword())))
                .map(userEntity -> modelMapper.map(userEntity, UserIdDto.class))
                .toEither(Error.badRequest("USER_NOT_FOUND"))
                .fold(Function.identity(), Success::ok);
    }

    public Response updateUserPasswordById(Long id, UserCredentialsDto userCredentialsDto){
        return Option.ofOptional(userRepository.findById(id))
                .peek(userEntity -> userEntity.setPassword(passwordEncoder.encode(userCredentialsDto.getPassword())))
                .map(userEntity -> modelMapper.map(userEntity, UserIdDto.class))
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
        userEntity = userRepository.save(userEntity);

        return modelMapper.map(userEntity, UserIdDto.class);
    }

    private UserEntity updateUserEntity (UserDetailsDto userDetailsDto, UserEntity userEntity) {

        userEntity.setFirstName(simpleValidatorEmptyInputString(
                userDetailsDto.getFirstName(),
                userEntity.getFirstName()));
        userEntity.setLastName(simpleValidatorEmptyInputString(
                userDetailsDto.getLastName(),
                userEntity.getLastName()));
        userEntity.setEmail(simpleValidatorEmptyInputString(
                userDetailsDto.getEmail(),
                userEntity.getEmail()));
        userEntity.setPhoneNumber(simpleValidatorEmptyInputString(
                userDetailsDto.getPhoneNumber(),
                userEntity.getPhoneNumber()));

        return userEntity;
    }

    private String simpleValidatorEmptyInputString(String inputNewValue, String inputOldValue){
        return inputNewValue.isBlank() ? inputOldValue : inputNewValue;
    }

    private List<UserDetailsDto> returnListOfUserDetailsDto(List<UserEntity> entities) {
        return entities.stream()
                .map(userEntity -> modelMapper.map(userEntity, UserDetailsDto.class))
                .collect(Collectors.toList());
    }

}
