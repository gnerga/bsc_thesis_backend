package com.nerga.travelCreatorApp.security;

import com.nerga.travelCreatorApp.security.dto.CreateUserDto;
import com.nerga.travelCreatorApp.security.dto.UserCredentialsDto;
import com.nerga.travelCreatorApp.security.dto.UserDetailsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/loggedUserDetails")
    public ResponseEntity findLoggedUserDetails(){
        return generalUserService.getUserDetailsForLoggedUser().toResponseEntity();
    }

    @GetMapping("user/findAll")
    public ResponseEntity findAllUser(){
        return generalUserService.findAllUsers().toResponseEntity();
    }

    @GetMapping("user/findOtherUsers")
    public ResponseEntity findAllUserWithoutAuthorizedUser(){
        return generalUserService.findAllUsersWithoutAuthorizedUser().toResponseEntity();
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
        return generalUserService.findUserDetailsById(id).toResponseEntity();
    }

    @PutMapping("user/update/byUsername/{username}")
    public ResponseEntity updateUserDetails(@PathVariable("username") String username,
                                            @RequestBody UserDetailsDto userDetailsDto){
        return generalUserService.updateUserByUsername(username, userDetailsDto).toResponseEntity();
    }

    @PutMapping("user/update/byId/{id}")
    public ResponseEntity updateUserDetails(@PathVariable("id") Long id,
                                            @RequestBody UserDetailsDto userDetailsDto){
        return generalUserService.updateUserById(id, userDetailsDto).toResponseEntity();
    }

    @PutMapping("user/updatepassword/byUsername/{username}")
    public ResponseEntity updatePassword(@PathVariable("username") String username,
                                         @RequestBody UserCredentialsDto userCredentialsDto){
        return generalUserService.updateUserPasswordByUsername(username, userCredentialsDto).toResponseEntity();
    }

    @PutMapping("user/updatepassword/byId/{id}")
    public ResponseEntity updatePassword(@PathVariable("id") Long id,
                                         @RequestBody UserCredentialsDto userCredentialsDto){
        return generalUserService.updateUserPasswordById(id, userCredentialsDto).toResponseEntity();
    }

    @DeleteMapping("user/delete/byUsername/{username}")
    public ResponseEntity deleteUser(@PathVariable("username") String username) {
        return generalUserService.deleteUserByUsername(username).toResponseEntity();
    }

    @DeleteMapping("user/delete/byId/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") Long id){
        return generalUserService.deleteUserById(id).toResponseEntity();
    }

}
