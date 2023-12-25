package com.main.commentservice.bootstrap;

import com.main.commentservice.domain.Comment;
import com.main.commentservice.repositories.CommentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CommentBootstrap implements CommandLineRunner {

    private final CommentRepository commentRepository;
    @Transactional
    @Override
    public void run(String... args) throws Exception {
        addCOmment(1, "boris@hotmail.com","boris", Arrays.asList("good", "bad","perfect", "I dont liek this article"), true);
        addCOmment(2, "loreana@hotmail.com","loreana", Arrays.asList("good", "bad","perfect", "I dont liek this article"), true);
        addCOmment(3, "andrijana@hotmail.com","andrijana", Arrays.asList("good", "bad","perfect", "I dont liek this article"), true);
        addCOmment(4, "darko@hotmail.com","darko", Arrays.asList("good", "bad","perfect", "I dont liek this article"), false);
    }

    public void addCOmment(Integer blogId, String email, String name, List<String> commentsContents, boolean enabled){
        commentsContents.forEach(comment -> {
            commentRepository.save(Comment.builder()
                                          .createdDate(LocalDateTime.now())
                                          .blogId(blogId)
                                          .email(email)
                                          .name(name)
                                          .enabled(enabled)
                                          .content(comment)
                                          .build());
        });
    }
}
