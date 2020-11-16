//package com.nerga.travelCreatorApp.user;
//
//import com.nerga.travelCreatorApp.security.auth.exceptions.BadUserCredentialsException;
//import com.nerga.travelCreatorApp.security.auth.exceptions.EmailAlreadyUseException;
//import com.nerga.travelCreatorApp.security.auth.exceptions.LoginAlreadyUsedException;
//import com.nerga.travelCreatorApp.security.auth.exceptions.MyUserNotFoundException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.util.StringUtils;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
////@Service("userService")
//public class UserService {
//
//    private UserOldRepository userRepository;
//    private UserSignUpMapper userSignUpMapper;
//
//    @Autowired
//    public UserService(UserOldRepository userRepository, UserSignUpMapper userSignUpMapper) {
//        this.userRepository = userRepository;
//        this.userSignUpMapper = userSignUpMapper;
//    }
//
//    public User registerNewUserAccount(UserSignUpDto account){
//
//        if (userRepository.existsByEmail(account.getEmail())) {
//            throw new EmailAlreadyUseException("Email already used");
//        }
//
//        if (userRepository.existsByUserLogin(account.getUserLogin())) {
//            throw new LoginAlreadyUsedException("Login already used");
//        }
//
//        User user = userSignUpMapper.transform(account);
//        user = userRepository.save(user);
//        return user;
//    }
//
//    public UserSignInDetailsDto sillyAuthenticate(String login, String password) throws Exception {
//
//        if (StringUtils.isEmpty(login) || StringUtils.isEmpty(password)){
//            throw new Exception("Empty credentials");
//        }
//
//        if (userRepository.existsByUserLogin(login)) {
//            Optional<User> user = userRepository.findUserByUserLogin(login);
//            if (user.isPresent()) {
//                if (user.get().getPassword().equals(password)){
//                    UserSignInDetailsDto userDetails = new UserSignInDetailsDto();
//                    userDetails.setLogin(user.get().getUserLogin());
//                    userDetails.setUserId(user.get().getUserId().intValue());
//                    userDetails.setJwt("jwtcode");
//                    return userDetails;
//                } else {
//                    throw new BadUserCredentialsException("Wrong password");
//                }
//
//            }
//        }
//
//        throw new BadUserCredentialsException("Invalid credentials");
//    }
//
//    public UserDetailsDto findUserById(Long userId){
//        return null;
//    }
//
//    public UserDetailsDto findUserByLogin(String login){
//        return null;
//    }
//
//    public List<UserDetailsDto> findAllUsers(){
//        Optional<List<User>> optionalUserList = Optional.ofNullable(Optional.of(userRepository.findAll()).orElseThrow(MyUserNotFoundException::new));
//        if (optionalUserList.isEmpty()) {
//            throw new MyUserNotFoundException();
//        }
//        return optionalUserList.get().stream()
//                .map(User::userToUserDetailsDto)
//                .collect(Collectors.toList());
//    }
//
//}
