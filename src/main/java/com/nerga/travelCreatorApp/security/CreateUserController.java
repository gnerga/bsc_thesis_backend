package com.nerga.travelCreatorApp.security;

import com.nerga.travelCreatorApp.security.dto.CreateUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class CreateUserController {

    private final CreateUserService createUserService;

    @Autowired
    public CreateUserController(CreateUserService createUserService) {
        this.createUserService = createUserService;
    }

    @PostMapping(path = "hello")
    public String sayHelloToTheWorld(){
        return "Hello";
    }

    @PostMapping
    public String registerNewUser(@RequestBody CreateUserDto createUserDto){
        createUserService.createUser(createUserDto);
        return "Done";
    }

}
