package com.main.blogservice.service;

import com.main.blogservice.config.JwtConst;
import com.main.blogservice.exceptions.BlogException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;


@Service
public class TokenService {

    public Integer getUserIdFromToken( String jwt){
        SecretKey key = Keys.hmacShaKeyFor(JwtConst.JwtSecret.getBytes(StandardCharsets.UTF_8));
        try{
            Claims claim = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();

            Integer userId = (Integer) claim.get("userId");
            if(userId == null)
                throw new BlogException("There is no user id in token!");

            return  userId;
        }catch (Exception e){
            throw new BlogException("Fail to parse received token!");
        }
    }
}
