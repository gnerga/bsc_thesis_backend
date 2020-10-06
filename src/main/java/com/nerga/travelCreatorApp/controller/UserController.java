package com.nerga.travelCreatorApp.controller;

import com.nerga.travelCreatorApp.dto.user.UserDetailsDto;
import com.nerga.travelCreatorApp.dto.user.UserSignInDetailsDto;
import com.nerga.travelCreatorApp.dto.user.UserSignInDto;
import com.nerga.travelCreatorApp.dto.user.UserSignUpDto;
import com.nerga.travelCreatorApp.exception.user.UserException;
import com.nerga.travelCreatorApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path="")
    public String helloUserMethod(){
        return "Hello !" ;
    }

    @PostMapping(path = "signup", consumes = "application/json")
    @ResponseBody
    public ResponseEntity signUp(@RequestBody UserSignUpDto accountDetails) {
        return createUserAccount(accountDetails) ? new ResponseEntity(HttpStatus.CREATED) : new ResponseEntity(HttpStatus.CONFLICT);
    }

    private boolean createUserAccount(UserSignUpDto accountDetails) {
        try {
            userService.registerNewUserAccount(accountDetails);
            return true;
        } catch (UserException exception) {
            return false;
        }
        catch (Exception e) {
            return false;
        }
    }

    @GetMapping(path="login/{login}/{password}")
    @ResponseBody
    public ResponseEntity login(@PathVariable("login") String login, @PathVariable("password") String password) throws Exception {
        try {
           UserSignInDetailsDto user = userService
                   //.sillyAuthenticate(authenticationDetails.getLogin(), authenticationDetails.getPassword());
                   .sillyAuthenticate(login, password);
                    return ResponseEntity.ok(user.toJson());
        } catch (UserException e) {
            return new ResponseEntity(e, HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping(path = "findAllUsers")
    public ResponseEntity findAll(){
        try {
            List<UserDetailsDto> usersList = userService.findAllUsers();
            return ResponseEntity.ok(usersList);
        } catch (UserException e) {
            return new ResponseEntity(e, HttpStatus.NOT_FOUND);
        }
    }

}
