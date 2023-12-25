package com.main.authservice.config;

import com.main.authservice.domain.User;
import com.main.authservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthenticationManager implements AuthenticationProvider {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String pwd = authentication.getCredentials().toString();

        User user = userRepository.findByEmail(username).orElseThrow(() -> new BadCredentialsException("Invalid username!"));

        if(passwordEncoder.matches(pwd, user.getPassword())){
            return new UsernamePasswordAuthenticationToken(username, pwd, Collections.singletonList(new SimpleGrantedAuthority(user.getAuthority().getAuthority())));
        }else{
            throw new BadCredentialsException("Invalid credentials!");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
