package com.nerga.travelCreatorApp.user;

import com.nerga.travelCreatorApp.security.GeneralUserService;
import com.nerga.travelCreatorApp.security.auth.User;
import com.nerga.travelCreatorApp.security.auth.UserDaoInterface;
import com.nerga.travelCreatorApp.security.auth.UserService;
import com.nerga.travelCreatorApp.security.auth.database.UserEntity;
import com.nerga.travelCreatorApp.security.auth.database.UserRepository;
import com.nerga.travelCreatorApp.security.dto.CreateUserDto;
import com.nerga.travelCreatorApp.security.dto.UserDetailsDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Mock
    UserDaoInterface userDaoInterface;

    UserService useCase;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        useCase = new UserService(userDaoInterface);
    }

    @Test
    void shouldReturnUserDetails(){
        // given
            String tUsername = "test_user";
            User tUser = getTestUser();
        // when
            when(userDaoInterface
                    .selectApplicationUserByUserName(tUsername))
                    .thenReturn(Optional.of(tUser));
        //then
            var result = useCase.loadUserByUsername(tUsername);
            assertEquals(result.getClass(), User.class);
    }

    private User getTestUser(){
        User userEntity = new User();

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
