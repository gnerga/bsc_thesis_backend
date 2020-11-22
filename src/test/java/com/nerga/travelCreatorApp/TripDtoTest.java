package com.nerga.travelCreatorApp;

import com.nerga.travelCreatorApp.security.auth.database.UserEntity;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;

public class TripDtoTest {

    ModelMapper modelMapper;

    @Before
    public void beforeTest(){
        modelMapper = new ModelMapper();
    }

    @Test
    public void whenConvertLocationToLocationDetailsDto_thenCorrect() {
        UserEntity userEntity = new UserEntity();
        UserEntity userEntity_2 = new UserEntity();

        userEntity.setId(1L);
        userEntity.setUsername("test_user");
        userEntity.setPassword("password_1");
        userEntity.setFirstName("Jan");
        userEntity.setLastName("Nowak");
        userEntity.setEmail("test@mail.com");
        userEntity.setPhoneNumber("1234516");

        userEntity_2.setId(2L);
        userEntity_2.setUsername("test_user_2");
        userEntity_2.setPassword("password_1");
        userEntity_2.setFirstName("Adam");
        userEntity_2.setLastName("Nowak");
        userEntity_2.setEmail("test2@mail.com");
        userEntity_2.setPhoneNumber("2234516");



    }

}
