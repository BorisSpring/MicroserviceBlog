package com.main.authservice.controllers;


import com.main.authservice.model.AuthResponseDto;
import com.main.authservice.model.UserDto;
import com.main.authservice.requests.CreateUserRequest;
import com.main.authservice.requests.LoginRequest;
import com.main.authservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

//@RestController
//@RequestMapping("/auth")
//@RequiredArgsConstructor
//@Validated
public class AuthController {

//    private final UserService userService;
//
//    @PostMapping("/login")
//    public ResponseEntity<AuthResponseDto> loginHandler(@Valid @RequestBody LoginRequest loginRequest){
//        return  ResponseEntity.ok(userService.loginUser(loginRequest));
//    }
//
//    @PostMapping("/signup")
//    public ResponseEntity<UserDto> signupHandler(@Valid @ModelAttribute CreateUserRequest request) throws IllegalStateException,  IOException {
//        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(request));
//    }
//
//    @GetMapping("/logged")
//    public ResponseEntity<UserDto> getLoggedUser(@RequestHeader("Authorization")String jwt) throws IOException {
//        return ResponseEntity.ok(userService.getLoggedUser(jwt));
//    }

}
