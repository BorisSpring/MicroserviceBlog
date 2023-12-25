package com.main.blogservice.service.clients;

import com.main.blogservice.model.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@FeignClient(name = "auth")
public interface UserServiceFeingClient {

    @GetMapping("/api/users/userImages")
    ResponseEntity<HashMap<Integer, UserDto>> getImage(@RequestParam List<Integer> userIds) throws IOException ;

}
