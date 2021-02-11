package com.nerga.travelCreatorApp.user;

import com.nerga.travelCreatorApp.common.response.Error;
import com.nerga.travelCreatorApp.security.GeneralUserService;
import com.nerga.travelCreatorApp.security.auth.database.UserEntity;
import com.nerga.travelCreatorApp.security.auth.database.UserRepository;
import com.nerga.travelCreatorApp.security.dto.CreateUserDto;
import com.nerga.travelCreatorApp.security.dto.UserCredentialsDto;
import com.nerga.travelCreatorApp.security.dto.UserDetailsDto;
import com.nerga.travelCreatorApp.security.dto.UserIdDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;


public class GeneralUserServiceTest {

    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    ModelMapper modelMapper;

    GeneralUserService useCase;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        useCase = new GeneralUserService(userRepository, passwordEncoder, modelMapper);
    }

    @Test
    void shouldCreateUserAndReturnUserDetailsDtoAndAcceptedStatus(){
        // give
        CreateUserDto tCreateUserDto = getTestCreateUserDto();
        UserEntity tUserEntity = getTestUserEntity();
        UserIdDto tUserIdDto = getTestUserIdDto();
        // when
        when(userRepository.existsByUsername(tCreateUserDto.getUsername())).thenReturn(false);

        when(passwordEncoder.encode(tCreateUserDto.getPassword())).thenReturn("asdasdasd");

        when(userRepository.save(any(UserEntity.class))).thenReturn(tUserEntity);

        when(modelMapper.map(tUserEntity, UserIdDto.class)).thenReturn(tUserIdDto);

        // then

        var response = useCase.createUser(tCreateUserDto);
        // assert

        Assertions.assertThat(response.toResponseEntity().getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(response.toResponseEntity().getBody()).isEqualToComparingFieldByField(tUserIdDto);

    }

    @Test
    void shouldReturnErrorWhenUserNameIsAlreadyInUsage(){
        // given
        CreateUserDto tCreateUserDto = getTestCreateUserDto();
        UserEntity tUserEntity = getTestUserEntity();
        UserIdDto tUserIdDto = getTestUserIdDto();
        Error tError = Error.badRequest("USERNAME_ALREADY_IN_USE");
        // when
        when(userRepository.existsByUsername(tCreateUserDto.getUsername())).thenReturn(true);
        // then
        var response = useCase.createUser(tCreateUserDto);
        // assert
        Assertions.assertThat(response.toResponseEntity().getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(response.toResponseEntity().getBody()).isEqualTo(tError.getCode());

    }


    @Test
    void shouldReturnUserDetailsDtoForGivenUsernameAndOkStatus(){
        // given
        String tUsername = "test_user";
        UserEntity tUserEntity = getTestUserEntity();
        UserDetailsDto tUserDetailsDto = getTestUserDetailsDto();
        // when
        when(userRepository.findByUsername(tUsername)).thenReturn(Optional.of(tUserEntity));
        when(modelMapper.map(tUserEntity, UserDetailsDto.class)).thenReturn(tUserDetailsDto);
        // then
        var response = useCase.findUserDetailsByUsername(tUsername);
        // assert
        Assertions.assertThat(response.toResponseEntity().getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.toResponseEntity().getBody()).isEqualTo(tUserDetailsDto);

    }

    @Test
    void shouldReturnUserDetailsDtoForGivenIdAndOkStatus(){
        // given
        long tUserId = 1L;
        UserEntity tUserEntity = getTestUserEntity();
        UserDetailsDto tUserDetailsDto = getTestUserDetailsDto();
        // when
        when(userRepository.findById(tUserId)).thenReturn(Optional.of(tUserEntity));
        when(modelMapper.map(tUserEntity, UserDetailsDto.class)).thenReturn(tUserDetailsDto);
        // then
        var response = useCase.findUserDetailsById(tUserId);
        // assert
        Assertions.assertThat(response.toResponseEntity().getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.toResponseEntity().getBody()).isEqualTo(tUserDetailsDto);

    }

    @Test
    void shouldReturnListAllUSer(){
        // given
        List<UserEntity> listOfUsers = new ArrayList<>();
        List<UserDetailsDto> listOfUsersDto = new ArrayList<>();
        UserEntity tUserEntity = getTestUserEntity();
        UserDetailsDto tUserDetailsDto = getTestUserDetailsDto();
        listOfUsers.add(tUserEntity);
        listOfUsersDto.add(tUserDetailsDto);

        // when
        when(userRepository.findAll()).thenReturn(listOfUsers);
        when(modelMapper.map(tUserEntity, UserDetailsDto.class)).thenReturn(tUserDetailsDto);
        // then
        var response = useCase.findAllUsers();
        // assert
        Assertions.assertThat(response.toResponseEntity().getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.toResponseEntity()
                .getBody()).isEqualTo(listOfUsersDto);

    }

    @Test
    void shouldReturnUserIdForGivenUsername(){
        // given
        String tUsername = "test_user";
        UserEntity tUserEntity = getTestUserEntity();
        UserIdDto tUserIdDto = getTestUserIdDto();
        // when
        when(userRepository.findByUsername(tUsername)).thenReturn(Optional.of(tUserEntity));
        when(modelMapper.map(tUserEntity, UserIdDto.class)).thenReturn(tUserIdDto);
        // then
        var response = useCase.findUserIdByUsername(tUsername);
        // assert
        Assertions.assertThat(response.toResponseEntity().getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.toResponseEntity()
                .getBody()).isEqualTo(tUserIdDto);


    }

    @Test
    void shouldUpdateUserByIdAndReturnUserDetailsDtoAndOkStatus(){
        // given
        long tUserId = 1L;
        UserEntity tUserEntity = getTestUserEntity();
        UserDetailsDto tUpdateUser = getTestUpdatedUserDetailsDto();
        UserEntity tUpdatedEntity = getTestUpdatedUserEntity();
        UserDetailsDto tUpdatedUserDto = getTestUpdatedUserDetailsDto();
        // when
        when(userRepository.findById(tUserId)).thenReturn(Optional.of(tUserEntity));
        when(userRepository.save(tUpdatedEntity)).thenReturn(tUpdatedEntity);
        when(modelMapper.map(tUpdatedEntity, UserDetailsDto.class)).thenReturn(tUpdatedUserDto);
        // then
        var response = useCase.updateUserById(tUserId, tUpdateUser);
        // assert
        Assertions.assertThat(response.toResponseEntity().getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.toResponseEntity()
                .getBody()).isEqualTo(tUpdatedUserDto);

    }

    @Test
    void shouldUpdateUserNameAndReturnUserDetailsDtoAndOkStatus(){
        String tUsername = "test_user";
        UserEntity tUserEntity = getTestUserEntity();
        UserDetailsDto tUpdateUser = getTestUpdatedUserDetailsDto();
        UserEntity tUpdatedEntity = getTestUpdatedUserEntity();
        UserDetailsDto tUpdatedUserDto = getTestUpdatedUserDetailsDto();
        // when
        when(userRepository.findByUsername(tUsername)).thenReturn(Optional.of(tUserEntity));
        when(userRepository.save(tUpdatedEntity)).thenReturn(tUpdatedEntity);
        when(modelMapper.map(tUpdatedEntity, UserDetailsDto.class)).thenReturn(tUpdatedUserDto);
        // then
        var response = useCase.updateUserByUsername(tUsername, tUpdateUser);
        // assert
        Assertions.assertThat(response.toResponseEntity().getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.toResponseEntity()
                .getBody()).isEqualTo(tUpdatedUserDto);
    }

    @Test
    void shouldUpdatePasswordByUserNameAndReturnUserIdAndOkStatus(){
        // given
        String tUsername = "test_user";
        UserEntity tUserEntity = getTestUserEntity();
        UserCredentialsDto tUserCredentials = getTestUserCredentials();
        UserIdDto tUserIdDto = getTestUserIdDto();
        // when
        when(userRepository.findByUsername(tUsername)).thenReturn(Optional.of(tUserEntity));
        when(passwordEncoder.encode(tUserCredentials.getPassword())).thenReturn("new_password");
        when(modelMapper.map(tUserEntity, UserIdDto.class)).thenReturn(tUserIdDto);
        // then
        var response = useCase.updateUserPasswordByUsername(tUsername, tUserCredentials);
        Assertions.assertThat(response.toResponseEntity().getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.toResponseEntity()
                .getBody()).isEqualTo(tUserIdDto);

    }

    @Test
    void shouldDeleteByIdAndReturnSuccessStatus(){

        // given
        long tUserId = 1L;
        UserEntity tUserEntity = getTestUserEntity();
        UserCredentialsDto tUserCredentials = getTestUserCredentials();
        UserIdDto tUserIdDto = getTestUserIdDto();
        // when
        when(userRepository.findById(tUserId)).thenReturn(Optional.of(tUserEntity));
        when(passwordEncoder.encode(tUserCredentials.getPassword())).thenReturn("new_password");
        when(userRepository.save(tUserEntity)).thenReturn(tUserEntity);
        when(modelMapper.map(tUserEntity, UserIdDto.class)).thenReturn(tUserIdDto);
        // then
        var response = useCase.updateUserPasswordById(tUserId, tUserCredentials);
        Assertions.assertThat(response.toResponseEntity().getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.toResponseEntity()
                .getBody()).isEqualTo(tUserIdDto);

    }

    @Test
    void shouldDeleteByUSerNameAndReturnSuccessStatus(){

        // given
        long tUserId = 1L;
        UserEntity tUserEntity = getTestUserEntity();

        // when
        when(userRepository.findById(tUserId)).thenReturn(Optional.of(tUserEntity));
        // then
        var response = useCase.deleteUserById(tUserId);

//        verify(userRepository, times(2));
        Assertions.assertThat(response.toResponseEntity().getStatusCode()).isEqualTo(HttpStatus.OK);


    }

    private UserIdDto getTestUserIdDto() {

        UserIdDto userIdDto = new UserIdDto();
        userIdDto.setUsername("test_user");
        userIdDto.setId(1L);
        return userIdDto;
    }

    private UserCredentialsDto getTestUserCredentials(){
        return new UserCredentialsDto("test_user", "new_pass");
    }

    private UserEntity getTestUserEntity(){
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setUsername("test_user");
        userEntity.setPassword("password_1");
        userEntity.setFirstName("Jan");
        userEntity.setLastName("Nowak");
        userEntity.setEmail("test@mail.com");
        userEntity.setPhoneNumber("1234516");
        return userEntity;
    }

    private UserEntity getTestUpdatedUserEntity(){
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setUsername("test_user");
        userEntity.setPassword("password_1");
        userEntity.setFirstName("Adam");
        userEntity.setLastName("Nowak");
        userEntity.setEmail("test@mail.com");
        userEntity.setPhoneNumber("1234516");
        return userEntity;
    }

    private CreateUserDto getTestCreateUserDto(){
        return new CreateUserDto(
                "test_user",
                "test",
                "Jan",
                "Nowak",
                "test@mail.com",
                "1234516"
        );
    }

    private UserDetailsDto getTestUpdatedUserDetailsDto(){
        return new UserDetailsDto(
                1L,
                "test_user",
                "Adam",
                "Nowak",
                "test@mail.com",
                "1234516");

    }

    private UserDetailsDto getTestUserDetailsDto(){
        return new UserDetailsDto(
                1L,
                "test_user",
                "Jan",
                "Nowak",
                "test@mail.com",
                "1234516");

    }
}
