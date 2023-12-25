package com.main.authservice.service;

import com.main.authservice.config.JwtConst;
import com.main.authservice.config.Settings;
import com.main.authservice.domain.User;
import com.main.authservice.exceptions.ResourceNotFoundException;
import com.main.authservice.exceptions.UserException;
import com.main.authservice.mappers.UserMapper;
import com.main.authservice.model.AuthResponseDto;
import com.main.authservice.model.UserDto;
import com.main.authservice.repositories.AuthorityRepository;
import com.main.authservice.repositories.UserRepository;
import com.main.authservice.requests.CreateUserRequest;
import com.main.authservice.requests.LoginRequest;
import com.main.authservice.requests.UpdatePasswordRequest;
import com.main.authservice.requests.UpdateUserInfoRequest;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

//@Service
//@RequiredArgsConstructor
public class UserServiceImpl  {

//    private final UserRepository userRepo;
//    private final PasswordEncoder passwordEncoder;
//    private final AuthorityRepository authorityRepo;
//    private final Settings settings;
//    private final UserMapper userMapper;
//
//    @Override
//    public User findByUserId(Integer userId) throws ResourceNotFoundException {
//        return userRepo.findById(userId)
//                        .orElseThrow(() -> new UserException("User doesnt exists"));
//    }
//    @Transactional
//    @Override
//    public void banUser(Integer userId) {
//        User user = findByUserId(userId);
//        if(user.isEnabled()){
//            user.setEnabled(false);
//            userRepo.save(user);
//        }
//    }
//
//    @Transactional
//    @Override
//    public void unBanUser(Integer userId) {
//        User user = findByUserId(userId);
//        if(!user.isEnabled()){
//            user.setEnabled(true);
//            userRepo.save(user);
//        }
//    }
//
//    @Override
//    @Transactional
//    public void updatePassword(UpdatePasswordRequest req, String jwt) {
//        User user = findByUserId(tokenService.getUserIdFromToken(jwt));
//
//        if(!passwordEncoder.matches(req.getOldPassword(), user.getPassword()))
//            throw new UserException("Incorrect Password");
//
//        if(!req.getNewPassword().equals(req.getRepeatedNewPassword()))
//            throw new UserException("Password Must Match");
//
//        user.setPassword(passwordEncoder.encode(req.getRepeatedNewPassword()));
//        userRepo.save(user);
//    }
//
//    @Transactional
//    @Override
//    public void deleteUser(Integer userId) {
//        if (!userRepo.existsById(userId))
//            throw new UserException("User with id " + userId + " not found");
//        userRepo.deleteById(userId);
//    }
//
//    @Override
//    public boolean existsByEmail(String email) {
//        return userRepo.existsByEmail(email);
//    }
//
//    @Override
//    public AuthResponseDto loginUser(LoginRequest req) {
//
//        User user = userRepo.findByEmail(req.getUsername())
//                .orElseThrow(() -> new BadCredentialsException("Invalid token received!"));
//
//        if(passwordEncoder.matches(req.getPassword(), user.getPassword())) {
//            try {
//                Authentication auth = new UsernamePasswordAuthenticationToken(user.getEmail(), null, List.of(new SimpleGrantedAuthority(user.getAuthority().getAuthority())));
//                SecurityContextHolder.getContext().setAuthentication(auth);
//
//                SecretKey key = Keys.hmacShaKeyFor(JwtConst.JwtSecret.getBytes(StandardCharsets.UTF_8));
//
//                String jwt = Jwts.builder()
//                        .setIssuedAt(new Date())
//                        .setIssuer("Boris Dimitrijevic")
//                        .setExpiration(new Date(new Date().getTime() + 24 * 60 * 60 * 60))
//                        .claim("username", req.getUsername())
//                        .claim("authorities", user.getAuthority().getAuthority())
//                        .claim("userId", user.getId())
//                        .signWith(key)
//                        .compact();
//
//                return new AuthResponseDto(true, jwt);
//            } catch (Exception e) {
//                throw new BadCredentialsException("Fail To Login, Try Again");
//            }
//        }else {
//            throw new BadCredentialsException("Invalid Credentials");
//        }
//
//    }
//
//    @Transactional
//    @Override
//    public UserDto createUser(CreateUserRequest request) throws IllegalStateException, IOException {
//
//        String imageName = null;
//        if(userRepo.existsByEmail(request.getEmail()))
//            throw  new UserException("There is alerdy user with same email adress!");
//
//        if(request.getImageFile() != null) {
//            imageName = UUID.randomUUID() + request.getImageFile().getOriginalFilename();
//            Path uploadPath = Paths.get(settings.uploadDir());
//
//            if(!Files.exists(uploadPath)) {
//                Files.createDirectories(uploadPath);
//            }
//
//            Files.copy(request.getImageFile().getInputStream(), uploadPath.resolve(imageName), StandardCopyOption.REPLACE_EXISTING);
//        }
//
//        User savedUser = userRepo.save(User.builder()
//                                .firstName(request.getFirstName())
//                                .lastName(request.getLastName())
//                                .email(request.getEmail())
//                                .enabled(true)
//                                .password(passwordEncoder.encode(request.getPassword()))
//                                .number(request.getNumber())
//                                .image(imageName == null ? "default.png" : imageName)
//                                .authority(authorityRepo.findByAuthority("admin")
//                                        .orElseThrow(() -> new ResourceNotFoundException("Authority not found!")))
//                                .build());
//
//        return  userMapper.userToUserDto(savedUser);
//    }
//
//
//    @Override
//    public Page<User> findAllUsers(String filterBy, int page) {
//        return userRepo.findAll(filterBy == null ? null : filterBy.equals("enabled"),
//                                    PageRequest.of((page > 0 ? (page - 1) : 0), 15));
//    }
//
//    @Override
//    public UserDto getLoggedUser(String jwt) throws IOException {
//        return  userMapper.userToUserDto(findByUserId(
//                                    tokenService.getUserIdFromToken(jwt)));
//    }
//
//
//
//    @Transactional
//    @Override
//    public void deleteImage(String jwt) throws IOException {
//
//        User user = findByUserId(tokenService.getUserIdFromToken(jwt));
//
//        if(user.getImageName() != null){
//            Path path = Paths.get(settings.uploadDir() + "/" + user.getImageName());
//            if(!Files.exists(path))
//                throw new UserException("User Image doesnt exists");
//
//            Files.delete(path);
//            user.setImageName("default.png");
//            userRepo.saveAndFlush(user);
//        }
//    }
//
//    @Transactional
//    @Override
//    public User updateUserInfo(UpdateUserInfoRequest request) {
//        User user = findByUserId(request.getId());
//        if(!user.getEmail().equals(request.getEmail()) && userRepo.existsByEmail(request.getEmail()))
//            throw new UserException("There is alerdy use with same email adress!");
//
//        user.setEmail(request.getEmail());
//        user.setFirstName(request.getFirstName());
//        user.setLastName(request.getLastName());
//        user.setNumber(request.getNumber());
//
//        return userRepo.saveAndFlush(user);
//    }
//
//    @Override
//    public HashMap<Integer, UserDto> findUserImages(List<Integer> userIds) throws IOException {
//        List<User> userList = userRepo.findByIdIn(userIds);
//        HashMap<Integer, UserDto> userImages = new HashMap<>();
//
//        for (User user : userList) {
//            userImages.put(user.getId(), userMapper.userToUserDto(user));
//        }
//        return userImages;
//    }
//
//    @Transactional
//    @Override
//    public void changeUserPicture(MultipartFile file, String jwt) throws IOException {
//
//        User user = findByUserId(tokenService.getUserIdFromToken(jwt));
//
//        Path path = null;
//        if(file != null && !file.isEmpty() &&  file.getOriginalFilename().endsWith(".jpg") || file.getOriginalFilename().endsWith(".png")) {
//
//            if (user.getImageName() != null && user.getImageName().length() > 1) {
//                path = Paths.get(settings.uploadDir() + "/" + user.getImageName());
//                if (Files.exists(path))
//                    Files.delete(path);
//            }
//
//            String imageName = UUID.randomUUID() + "-" + file.getOriginalFilename();
//
//            path = Paths.get(settings.uploadDir());
//            if (!Files.exists(path)) {
//                Files.createDirectories(path);
//            }
//
//            Files.copy(file.getInputStream(), path.resolve(imageName), StandardCopyOption.REPLACE_EXISTING);
//
//            user.setImageName(imageName);
//            userRepo.saveAndFlush(user);
//        }
//    }

}