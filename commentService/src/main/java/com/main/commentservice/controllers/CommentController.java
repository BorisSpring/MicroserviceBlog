package com.main.commentservice.controllers;


import com.main.commentservice.domain.Comment;
import com.main.commentservice.exceptions.CommentException;
import com.main.commentservice.model.BlogCommentCountDto;
import com.main.commentservice.model.CommentDto;
import com.main.commentservice.requests.CreateCommentRequest;
import com.main.commentservice.service.CommentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCommentHandler(@PathVariable(name = "commentId") Integer commentId) throws CommentException {
        commentService.deleteComment(commentId);
    }

    @PostMapping
    public ResponseEntity<Comment> addCommentHandler(@Valid @RequestBody CreateCommentRequest request) throws CommentException{
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.addComment(request));
    }

    @PutMapping("/disable/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public void disableCommentHandler(@PathVariable(name = "commentId") Integer blogId) throws CommentException{
        commentService.disableComment(blogId);
    }

    @PutMapping("/enable/{commentId}")
    public void enableCommentHandler(@PathVariable(name = "commentId") Integer blogId) throws CommentException{
        commentService.enableComment(blogId);
    }

    @GetMapping
    public ResponseEntity<Page<Comment>> findAllCommentsHandler(@RequestParam(name="page", defaultValue="1") Integer page,
                                                                @RequestParam(name="filterBy", required= false) String filterBy,
                                                                @RequestParam( name = "blogId", required= false) Integer blogId){
        return ResponseEntity.ok(commentService.findComments(page, filterBy == null ? null : filterBy.equals("readed"), blogId));
    }

    @GetMapping("/blog")
    public ResponseEntity<List<CommentDto>> findCommentsByBlogId(@Positive(message = "Blog id must be greather then zero!") @RequestParam(name = "blogId") Integer blogId){
        return ResponseEntity.ok(commentService.findByBlogId(blogId));
    }

    @GetMapping("/numberOfComments")
    public ResponseEntity<List<BlogCommentCountDto>> findNumberOfCommentsByBlogId(@RequestParam(name = "blogIds") List<Integer> blogId){
        return ResponseEntity.ok(commentService.countNumberOfComments(blogId));
    }
}
