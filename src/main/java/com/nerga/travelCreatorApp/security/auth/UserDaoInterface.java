package com.nerga.travelCreatorApp.security.auth;

import java.util.Optional;

public interface UserDaoInterface {

    Optional<User> selectApplicationUserByUserName(String username);

}
