package com.main.authservice.service;

import com.main.authservice.domain.User;
import com.main.authservice.exceptions.ResourceNotFoundException;
import com.main.authservice.mappers.UserMapper;
import com.main.authservice.model.UserDto;
import com.main.authservice.model.VerifyPasswordRequest;
import com.main.authservice.model.VerifyPasswordResponse;
import com.main.authservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class KeyCloakUserServiceImpl implements KeyCloakUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    @Override
    public UserDto findUserByEmail(String email) throws IOException {
        return userMapper.userToUserDto(userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with email " + email + " not found!")));
    }

    @Override
    public VerifyPasswordResponse findUserByEmail(String email, VerifyPasswordRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with email " + email + " not found!"));

        return VerifyPasswordResponse.builder()
                                            .isValid(passwordEncoder.matches(request.getPassword(), user.getPassword()))
                                            .build();
    }


}
