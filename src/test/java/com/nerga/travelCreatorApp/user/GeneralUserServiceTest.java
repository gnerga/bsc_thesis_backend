package com.nerga.travelCreatorApp.user;

import com.nerga.travelCreatorApp.common.response.Error;
import com.nerga.travelCreatorApp.security.GeneralUserService;
import com.nerga.travelCreatorApp.security.auth.database.UserEntity;
import com.nerga.travelCreatorApp.security.auth.database.UserRepository;
import com.nerga.travelCreatorApp.security.dto.CreateUserDto;
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

import static org.mockito.Mockito.when;

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
        when(userRepository.save(tUserEntity)).thenReturn(tUserEntity);
        when(modelMapper.map(tUserEntity, UserIdDto.class)).thenReturn(tUserIdDto);

        // then

        var response = useCase.createUser(tCreateUserDto);

        // assert

    Assertions.assertThat(response.toResponseEntity().getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        Assertions.assertThat(response.toResponseEntity().getBody()).isEqualToComparingFieldByField(tUserIdDto);

    }

    @Test
    void shouldReturnErrorWhenUserNameIsAlreadyInUsage(){
        // give
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
    void shouldReturnErrorWhenGivenUserNameIsInUsage(){

    }

    @Test
    void shouldReturnUserDetailsDtoForGivenUsernameAndOkStatus(){

    }

    @Test
    void shouldReturnUserDetailsDtoForGivenIdAndOkStatus(){

    }

    @Test
    void shouldReturnUserIdForGivenUsername(){

    }

    @Test
    void shouldUpdateUserByIdAndReturnUserDetailsDtoAndOkStatus(){

    }

    @Test
    void shouldUpdateUserNameAndReturnUserDetailsDtoAndOkStatus(){

    }

    @Test
    void shouldUpdatePasswordByUserNameAndReturnUserIdAndOkStatus(){

    }

    @Test
    void shouldDeleteByIdAndReturnSuccessStatus(){

    }

    @Test
    void shouldDeleteByUSerNameAndReturnSuccessStatus(){

    }

    private UserIdDto getTestUserIdDto() {

        UserIdDto userIdDto = new UserIdDto();
        userIdDto.setUsername("test_user");
        userIdDto.setId(1L);
        return userIdDto;
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
