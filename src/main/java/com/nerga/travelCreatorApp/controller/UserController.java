package com.nerga.travelCreatorApp.controller;

import com.nerga.travelCreatorApp.dto.user.UserSignUpDto;
import com.nerga.travelCreatorApp.exception.user.UserException;
import com.nerga.travelCreatorApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.StringUtils;

@RestController("/")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path="/")
    public String helloUserMethod(){
        return "Hello !" ;
    }

    @PostMapping(path = "signUp", consumes = "application/json")
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

//    @PostMapping(path="login", consumes = "application/json")
//    public ResponseEntity logIn(@RequestBody UserSignInDto authenticationDetails) throws Exception {
//
//    }






}
