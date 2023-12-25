package com.main.authservice.controllers;

import com.main.authservice.domain.User;
import com.main.authservice.model.UserDto;
import com.main.authservice.requests.UpdatePasswordRequest;
import com.main.authservice.requests.UpdateUserInfoRequest;
import com.main.authservice.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

//@RestController
//@RequestMapping("/api/users")
//@RequiredArgsConstructor
//@Validated
public class UserController {

//    private final UserService userService;
//
//    @GetMapping
//    public ResponseEntity<Page<User>> findAllUsersHandler(@RequestParam(name = "filterBy", required = false) String filterBy ,
//                                                          @Positive(message = "page Number must be greather then zero") @RequestParam(name = "page" , defaultValue = "1") int page){
//        return ResponseEntity.ok(userService.findAllUsers(filterBy,page));
//    }
//
//    @GetMapping("userImages")
//    public ResponseEntity<HashMap<Integer, UserDto>> getImage(@RequestParam List<Integer> userIds) throws IOException {
//        return ResponseEntity.ok(userService.findUserImages(userIds));
//    }
//
//    @PutMapping("/unban/{userId}")
//    @ResponseStatus(HttpStatus.OK)
//    public void unbanUserHandler(@Positive(message = "User id must be greather then zero") @PathVariable(name = "userId") Integer userId){
//        userService.unBanUser(userId);
//    }
//
//    @PutMapping("/ban/{userId}")
//    @ResponseStatus(HttpStatus.OK)
//    public void  banUserHandler(@Positive(message = "User id must be greather then zero") @PathVariable(name = "userId") Integer userId){
//        userService.banUser(userId);
//    }
//
//    @DeleteMapping("/{userId}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void deleteUserHandler(@Positive(message = "User id must be greather then zero") @PathVariable Integer userId){
//        userService.deleteUser(userId);
//    }
//
//    @PutMapping("/changePassword")
//    @ResponseStatus(HttpStatus.OK)
//    public void updatePasswordHandler(@Valid @RequestBody UpdatePasswordRequest updatePasswordRequest,
//                                      @RequestHeader("Authorization") String jwt ){
//        userService.updatePassword(updatePasswordRequest, jwt);
//    }
//
//
//    @DeleteMapping("/image")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void deleteImageHandler(@RequestHeader("Authorization") String jwt) throws IOException{
//        userService.deleteImage(jwt);
//    }
//
//    @PostMapping("/updateImage")
//    @ResponseStatus(HttpStatus.OK)
//    public void updateImageHandler(@RequestParam(name = "image") MultipartFile file,
//                                   @RequestHeader("Authorization") String jwt) throws IOException{
//        userService.changeUserPicture(file, jwt);
//    }
//
//    @PutMapping("/updateInfo")
//    public ResponseEntity<User> updateUserInfo(@Valid @RequestBody UpdateUserInfoRequest request){
//        return ResponseEntity.ok(userService.updateUserInfo(request));
//    }
}