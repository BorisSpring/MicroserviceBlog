package com.main.authservice.bootstrap;

import com.main.authservice.domain.Authority;
import com.main.authservice.domain.User;
import com.main.authservice.repositories.AuthorityRepository;
import com.main.authservice.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Bootstrap implements CommandLineRunner {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void run(String... args) throws Exception {

        Authority admin = saveAuthority("admin");
        saveUser("123456789", admin, "default.png", "Loreana", "Beatovic", "12345", "loreana@hotmail.com", true);
        saveUser("123456788", admin, "boris.png", "Boris", "Dimitrijevic", "12345", "boris@hotmail.com", true);
        saveUser("123456780", admin, "default.png", "Andrijana", "Molnar", "12345", "andrijana@hotmail.com", false);
        saveUser("123456785", admin, "default.png", "Darko", "Molnar", "12345", "darko@hotmail.com", false);
        saveUser("123456784", admin, "default.png", "Jelena", "Beatovic", "12345", "jelena@hotmail.com", false);

    }

    public void saveUser(String number, Authority authority, String imageName, String firstName , String lastName, String password, String email, boolean enabled){
          userRepository.save(User.builder()
                        .authority(authority)
                        .image(imageName)
                        .firstName(firstName)
                        .lastName(lastName)
                        .password(passwordEncoder.encode(password))
                        .email(email)
                        .enabled(enabled)
                        .number(number)
                        .build());
    }

    public Authority saveAuthority(String name){
        return  authorityRepository.saveAndFlush(Authority.builder()
                                        .authority("admin")
                                        .build());
    }
}
