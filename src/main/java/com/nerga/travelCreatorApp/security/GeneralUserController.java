package com.nerga.travelCreatorApp.security;

import com.nerga.travelCreatorApp.common.response.Response;
import com.nerga.travelCreatorApp.security.auth.database.UserEntity;
import com.nerga.travelCreatorApp.security.dto.CreateUserDto;
import com.nerga.travelCreatorApp.security.dto.UserCredentialsDto;
import com.nerga.travelCreatorApp.security.dto.UserIdDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.UserCredentialsDataSourceAdapter;
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
    public ResponseEntity findUserId(@PathVariable("username") String username){
        return generalUserService.findUserIdByUsername(username).toResponseEntity();
    }

    @GetMapping("user/details/byUsername/{username}")
    public ResponseEntity findUserDetails(@PathVariable("username") String username){
        return generalUserService.findUserDetailsByUsername(username).toResponseEntity();
    }

    @GetMapping("user/details/byId/{id}")
    public ResponseEntity findUserDetails(@PathVariable("id") Long id){
        return null;
    }

    @PutMapping("user/update/byUsername/{username}")
    public ResponseEntity updateUserDetails(@PathVariable("username") String username,
                                            @RequestBody CreateUserDto createUserDto){
        return null;
    }

    @PutMapping("user/update/byId/{id}")
    public ResponseEntity updateUserDetails(@PathVariable("id") Long id,
                                            @RequestBody CreateUserDto createUserDto){
        return null;
    }

    @PutMapping("user/updatepassword/byUsername/{username}")
    public ResponseEntity updatePassword(@PathVariable("username") String name,
                                         @RequestBody UserCredentialsDto userCredentialsDto){
        return null;
    }

    @PutMapping("user/updatepassword/byId/{id}")
    public ResponseEntity updatePassword(@PathVariable("id") Long id,
                                         @RequestBody UserCredentialsDto userCredentialsDto){
        return null;
    }

    @DeleteMapping("user/delete/byUsername/{username}")
    public ResponseEntity deleteUser(@PathVariable("username") String username) {
        return null;
    }

    @DeleteMapping("user/delete/byId/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") Long id){
        return null;
    }


}
