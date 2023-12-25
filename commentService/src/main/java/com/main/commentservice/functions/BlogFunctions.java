package com.main.commentservice.functions;

import com.main.commentservice.functions.events.ValidateBlogResult;
import com.main.commentservice.service.CommentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
public class BlogFunctions {

    private final StreamBridge streamBridge;
    private final CommentServiceImpl commentService;

//    @Bean
//    public Consumer<ValidateBlogResult> validateBlogResult(){
//        return commentService::setBlogValidationResult;
//    }

}
