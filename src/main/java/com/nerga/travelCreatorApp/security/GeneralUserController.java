package com.nerga.travelCreatorApp.security;

import com.nerga.travelCreatorApp.common.response.Response;
import com.nerga.travelCreatorApp.security.auth.database.UserEntity;
import com.nerga.travelCreatorApp.security.dto.CreateUserDto;
import com.nerga.travelCreatorApp.security.dto.UserIdDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/")
public class GeneralUserController {

    private final GeneralUserService generalUserService;

    @Autowired
    public GeneralUserController(GeneralUserService generalUserService) {
        this.generalUserService = generalUserService;
    }

    @PostMapping(path="/register")
    public ResponseEntity registerNewUser(@RequestBody CreateUserDto createUserDto){
        return generalUserService.createUser(createUserDto).toResponseEntity();
    }

    @GetMapping("user/findId/{username}")
//    public Map<String, String> findUserByUsername(@PathVariable("username") String username){
    public UserIdDto findUserByUsername(@PathVariable("username") String username){
        return generalUserService.getUserByUsername(username);
    }

    @GetMapping("user/details/{username}")
    public Map<String, String> findUserDetails(@PathVariable("username") String username){
        return generalUserService.getUserDetailsByUsername(username);
    }

}
