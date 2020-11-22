package com.nerga.travelCreatorApp;

import com.nerga.travelCreatorApp.location.Location;
import com.nerga.travelCreatorApp.location.dto.LocationCreateDto;
import com.nerga.travelCreatorApp.location.dto.LocationDetailsDto;
import com.nerga.travelCreatorApp.security.auth.User;
import com.nerga.travelCreatorApp.security.auth.database.UserEntity;
import com.nerga.travelCreatorApp.security.dto.CreateUserDto;
import com.nerga.travelCreatorApp.security.dto.UserDetailsDto;
import com.nerga.travelCreatorApp.security.dto.UserIdDto;
import com.nerga.travelCreatorApp.trip.dto.TripCreateDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.Assert.assertEquals;

public class UserTest {

    private ModelMapper modelMapper;

    @Before
    public void beforeTest(){
        modelMapper = new ModelMapper();
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

        Assert.assertEquals(createUserDto.getUsername(), userEntity.getUsername());
        Assert.assertEquals(createUserDto.getFirstName(), userEntity.getFirstName());
        Assert.assertEquals(createUserDto.getLastName(), userEntity.getLastName());
        Assert.assertEquals(createUserDto.getEmail(), userEntity.getEmail());
        Assert.assertEquals(createUserDto.getPhoneNumber(), userEntity.getPhoneNumber());

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

        Assert.assertEquals(userEntity.getId(), userDetailsDto.getId());
        Assert.assertEquals(userEntity.getUsername(), userDetailsDto.getUsername());
        Assert.assertEquals(userEntity.getFirstName(), userDetailsDto.getFirstName());
        Assert.assertEquals(userEntity.getLastName(), userDetailsDto.getLastName());
        Assert.assertEquals(userEntity.getEmail(), userDetailsDto.getEmail());
        Assert.assertEquals(userEntity.getPhoneNumber(), userDetailsDto.getPhoneNumber());

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

        Assert.assertEquals(userEntity.getId(), userIdDto.getId());
        Assert.assertEquals(userEntity.getUsername(), userIdDto.getUsername());
    }

    @Test
    public void whenConvertTripToTripDetailsDto_thenCorrect(){



    }

    @Test
    public void whenConvertTripCreateDtoToTrip_thenCorrect(){

        TripCreateDto tripCreateDto = new TripCreateDto();


    }


}
