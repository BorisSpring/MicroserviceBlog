package com.main.blogservice.service.clients;

import com.main.blogservice.model.BlogCommentCountDto;
import com.main.blogservice.model.CommentDto;
import jakarta.validation.constraints.Positive;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Primary
@FeignClient(name = "comments")
public interface CommentFeingClient {

    @GetMapping("/api/comments/numberOfComments")
    ResponseEntity<List<BlogCommentCountDto>> findNumberOfCommentsByBlogId(@RequestParam(name = "blogIds") List<Integer> blogId);

    @GetMapping("/api/comments/blog")
    ResponseEntity<List<CommentDto>> findCommentsByBlogId(@Positive(message = "Blog id must be greather then zero!") @RequestParam(name = "blogId") Integer blogId);

}
