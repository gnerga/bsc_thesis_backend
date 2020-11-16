package com.nerga.travelCreatorApp.security.auth;

import com.nerga.travelCreatorApp.security.auth.database.UserEntity;
import com.nerga.travelCreatorApp.security.auth.database.UserRepository;

import java.util.Optional;

public class UserDao implements UserDaoInterface {

    private final UserRepository userRepository;

    public UserDao(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> selectApplicationUserByUserName(String username) {
        return userRepository.findByUsername(username).map(UserEntity::getUserFromEntity);
    }
}
