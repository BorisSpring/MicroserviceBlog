package com.main.authservice.service;

import com.main.authservice.domain.User;
import com.main.authservice.model.UserDto;
import com.main.authservice.model.VerifyPasswordRequest;
import com.main.authservice.model.VerifyPasswordResponse;

import java.io.IOException;

public interface KeyCloakUserService {

    UserDto findUserByEmail(String email) throws IOException;

    VerifyPasswordResponse findUserByEmail(String email, VerifyPasswordRequest password);

}
