package com.main.messageservice.bootstrap;

import com.main.messageservice.domain.Message;
import com.main.messageservice.repositories.MessageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Bootstrap implements CommandLineRunner {

    private final MessageRepository messageRepository;

    @Transactional
    @Override
    public void run(String... args) throws Exception {

        saveMessage("Boris","Hello i want to blog for you? Is this possible", "borisdimitrijevicit@gmail.com", false);
        saveMessage("Boris","I think u was insulting some personality with blog by id of 12.", "borisdimitrijevicit@gmail.com", false);
        saveMessage("Boris","I dont like blog with id 15.", "borisdimitrijevicit@gmail.com", true);
        saveMessage("Loreana","My name is loreana and i want to start blogging for you", "loreana@gmail.com", true);
        saveMessage("Andrijana","My name is Andrijana and i want to start blogging for you", "andrijana@gmail.com", true);
        saveMessage("Jelena","My name is Jelena and i want to start blogging for you", "jelena@gmail.com", true);
        saveMessage("Darko","My name is Darko and i want to start blogging for you", "jelena@gmail.com", true);


    }

    public void saveMessage(String name, String message, String email, boolean status){
        messageRepository.save(Message.builder()
                                    .name(name)
                                    .message(message)
                                    .email(email)
                                    .readed(status)
                                    .build());
    }
}
