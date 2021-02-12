package com.nerga.travelCreatorApp.user;

import com.nerga.travelCreatorApp.security.auth.database.UserEntity;
import com.nerga.travelCreatorApp.security.dto.CreateUserDto;
import com.nerga.travelCreatorApp.security.dto.UserDetailsDto;
import com.nerga.travelCreatorApp.security.dto.UserIdDto;
import com.nerga.travelCreatorApp.trip.dto.TripCreateDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class UserTest {

    private ModelMapper modelMapper;

    @BeforeEach
    public void beforeTest(){
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @Test
    public void whenConvertCreateUserDtoToUserEntity_thenCorrect(){

        CreateUserDto createUserDto = new CreateUserDto();

        createUserDto.setUsername("test_user");
        createUserDto.setPassword("password_1");
        createUserDto.setFirstName("Jan");
        createUserDto.setLastName("Nowak");
        createUserDto.setEmail("test@mail.com");
        createUserDto.setPhoneNumber("1234516");

        UserEntity userEntity = modelMapper.map(createUserDto, UserEntity.class);

        assertEquals(createUserDto.getUsername(), userEntity.getUsername());
        assertEquals(createUserDto.getFirstName(), userEntity.getFirstName());
        assertEquals(createUserDto.getLastName(), userEntity.getLastName());
        assertEquals(createUserDto.getEmail(), userEntity.getEmail());
        assertEquals(createUserDto.getPhoneNumber(), userEntity.getPhoneNumber());

    }

    @Test
    public void whenConvertUserEntityToUserDetailsDto_thenCorrect(){

        UserEntity userEntity = new UserEntity();

        userEntity.setId(1L);
        userEntity.setUsername("test_user");
        userEntity.setPassword("password_1");
        userEntity.setFirstName("Jan");
        userEntity.setLastName("Nowak");
        userEntity.setEmail("test@mail.com");
        userEntity.setPhoneNumber("1234516");

        UserDetailsDto userDetailsDto = modelMapper.map(userEntity, UserDetailsDto.class);

        assertEquals(userEntity.getId(), userDetailsDto.getId());
        assertEquals(userEntity.getUsername(), userDetailsDto.getUsername());
        assertEquals(userEntity.getFirstName(), userDetailsDto.getFirstName());
        assertEquals(userEntity.getLastName(), userDetailsDto.getLastName());
        assertEquals(userEntity.getEmail(), userDetailsDto.getEmail());
        assertEquals(userEntity.getPhoneNumber(), userDetailsDto.getPhoneNumber());

    }

    @Test
    public void whenConvertUserEntityToUserIdDto_thenCorrect(){

        UserEntity userEntity = new UserEntity();

        userEntity.setId(1L);
        userEntity.setUsername("test_user");
        userEntity.setPassword("password_1");
        userEntity.setFirstName("Jan");
        userEntity.setLastName("Nowak");
        userEntity.setEmail("test@mail.com");
        userEntity.setPhoneNumber("1234516");

        UserIdDto userIdDto = modelMapper.map(userEntity, UserIdDto.class);

        assertEquals(userEntity.getId(), userIdDto.getId());
        assertEquals(userEntity.getUsername(), userIdDto.getUsername());
    }



}
