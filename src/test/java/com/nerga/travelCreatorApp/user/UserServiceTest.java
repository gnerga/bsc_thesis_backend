package com.nerga.travelCreatorApp.user;

import com.nerga.travelCreatorApp.security.GeneralUserService;
import com.nerga.travelCreatorApp.security.auth.UserService;
import com.nerga.travelCreatorApp.security.auth.database.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserServiceTest {

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
    void shouldReturnUserEntityWithGivenId(){




    }

}
