package com.nerga.travelCreatorApp.service;

import com.nerga.travelCreatorApp.dto.user.UserSignUpDto;
import com.nerga.travelCreatorApp.dto.user.UserSignUpMapper;
import com.nerga.travelCreatorApp.exception.user.BadUserCredentialsException;
import com.nerga.travelCreatorApp.exception.user.EmailAlreadyUseException;
import com.nerga.travelCreatorApp.exception.user.LoginAlreadyUsedException;
import com.nerga.travelCreatorApp.model.User;
import com.nerga.travelCreatorApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service("userService")
public class UserService {

    private UserRepository userRepository;
    private UserSignUpMapper userSignUpMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserSignUpMapper userSignUpMapper) {
        this.userRepository = userRepository;
        this.userSignUpMapper = userSignUpMapper;
    }

    public User registerNewUserAccount(UserSignUpDto account){

        if (userRepository.existsByEmail(account.getEmail())) {
            throw new EmailAlreadyUseException("Email already used");
        }

        if (userRepository.existsByUserLogin(account.getUserLogin())) {
            throw new LoginAlreadyUsedException("Login already used");
        }

        User user = userSignUpMapper.transform(account);
        user = userRepository.save(user);
        return user;
    }

    public boolean sillyAuthenticate(String login, String password) throws Exception {

        if (StringUtils.isEmpty(login) || StringUtils.isEmpty(password)){
            throw new Exception("Empty credentials");
        }

        if (userRepository.existsByUserLogin(login)) {
            Optional<User> user = userRepository.findUserByUserLogin(login);
            if (user.isPresent()) {
                return user.get().getPassword().equals(password);
            }
        }

        throw new BadUserCredentialsException("Invalid credentials");
    }

}
