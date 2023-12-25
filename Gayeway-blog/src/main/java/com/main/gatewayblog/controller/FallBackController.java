package com.main.gatewayblog.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Map;

@RequestMapping("/fallback")
@RestController
public class FallBackController {

    @GetMapping
    public Mono<String> getFallbackError(){
        return Mono.just("Error occured during fallback!");
    }

    @GetMapping("/token")
    public Map<String, Object> getToken(@AuthenticationPrincipal Jwt jwt){
       return Collections.singletonMap("principal", jwt.getClaims());
    }

}
