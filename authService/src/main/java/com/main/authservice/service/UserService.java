package com.main.authservice.service;

import com.main.authservice.domain.User;
import com.main.authservice.exceptions.ResourceNotFoundException;
import com.main.authservice.model.AuthResponseDto;
import com.main.authservice.model.UserDto;
import com.main.authservice.requests.CreateUserRequest;
import com.main.authservice.requests.LoginRequest;
import com.main.authservice.requests.UpdatePasswordRequest;
import com.main.authservice.requests.UpdateUserInfoRequest;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public interface UserService {

//    UserDto getLoggedUser(String jwt) throws IOException;
//
//    Page<User> findAllUsers(String filterBy, int pageNumber);
//
//    void changeUserPicture(MultipartFile file, String jwt) throws IOException;
//
//    void banUser(Integer userId);
//
//    void unBanUser(Integer userId);
//
//    void updatePassword(UpdatePasswordRequest updatePasswordRequest, String jwt);
//
//    void deleteUser(Integer userId);
//
//    boolean existsByEmail(String email);
//
//    User findByUserId(Integer userId) throws ResourceNotFoundException;
//
//    AuthResponseDto loginUser(LoginRequest loginRequest);
//    UserDto createUser(CreateUserRequest request) throws IllegalStateException, IOException;
//
////    void deleteImage(String jwt) throws IOException;
//
//    User updateUserInfo(UpdateUserInfoRequest updateUserInfoRequest);
//
//    HashMap<Integer,UserDto> findUserImages(List<Integer> userIds) throws IOException;
}
