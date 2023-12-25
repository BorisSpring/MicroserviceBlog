package com.main.authservice.controllers;

import com.main.authservice.domain.User;
import com.main.authservice.model.UserDto;
import com.main.authservice.model.VerifyPasswordRequest;
import com.main.authservice.model.VerifyPasswordResponse;
import com.main.authservice.service.KeyCloakUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class KeycloakController {

    private final KeyCloakUserService keyCloakUserService;
    @GetMapping("/{username}")
    public ResponseEntity<UserDto>  getUser(@PathVariable("username") String username) throws IOException {
        System.out.println("username: " + username);
        return  ResponseEntity.ok(keyCloakUserService.findUserByEmail(username));
    }

    @PostMapping("/{username}/verify-password")
    public ResponseEntity<VerifyPasswordResponse> verifyUserPassword(@PathVariable("username") String username,
                                                                     @RequestBody VerifyPasswordRequest password){
        return ResponseEntity.ok(keyCloakUserService.findUserByEmail(username,password));
    }
}
