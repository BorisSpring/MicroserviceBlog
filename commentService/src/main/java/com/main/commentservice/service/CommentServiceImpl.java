package com.main.commentservice.service;

import com.main.commentservice.domain.Comment;
import com.main.commentservice.exceptions.CommentException;
import com.main.commentservice.functions.events.ValidateBlogRequest;
import com.main.commentservice.functions.events.ValidateBlogResult;
import com.main.commentservice.mappers.CommentMapper;
import com.main.commentservice.model.BlogCommentCountDto;
import com.main.commentservice.model.CommentDto;
import com.main.commentservice.repositories.CommentRepository;
import com.main.commentservice.requests.CreateCommentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final StreamBridge streamBridge;
    private final CommentMapper commentMapper;

    @Override
    public Comment addComment(CreateCommentRequest request) throws CommentException {
        streamBridge.send("validate-blog",new ValidateBlogRequest(request));

        return commentRepository.save(Comment.builder()
                                    .enabled(true)
                                    .email(request.getEmail())
                                    .content(request.getContent())
                                    .blogId(request.getBlogId())
                                    .name(request.getName())
                                    .build());
    }

    @Override
    public void disableComment(Integer commentId) throws CommentException {
        commentRepository.findById(commentId).ifPresentOrElse(comment -> {
            if(comment.isEnabled()){
                comment.setEnabled(false);
                commentRepository.save(comment);
            }
        }, () -> {
            throw new CommentException("Comment with id " + commentId + " doesnt exists");
        });
    }

    @Override
    public void enableComment(Integer commentId) throws CommentException {
        commentRepository.findById(commentId).ifPresentOrElse(comment -> {
            if(!comment.isEnabled()){
                comment.setEnabled(true);
                commentRepository.save(comment);
            }
        }, () -> {
            throw new CommentException("Comment with id " + commentId + " doesnt exists");
        });
    }

    @Override
    public void deleteComment(Integer commentId) throws CommentException {
        if(!commentRepository.existsById(commentId))
            throw new CommentException("Comment with id " + commentId + " doesnt exists");
        commentRepository.deleteById(commentId);
    }

    @Override
    public Comment findById(Integer commentId) throws CommentException {
        return commentRepository.findById(commentId)
                         .orElseThrow(() -> new CommentException("Comment with id " + commentId + " doesnt exists"));
    }

    @Override
    public PageImpl<Comment> findComments(int pageNumber, Boolean status, Integer blogId) {
        return commentRepository.findComments(status, blogId, PageRequest.of((pageNumber - 1), 10));
    }

    @Override
    public List<CommentDto> findByBlogId(Integer blogId) {
       return commentRepository.findByBlogId(blogId)
                                   .stream()
                                   .map(commentMapper::commentToDto)
                                   .collect(Collectors.toList());
    }



    @Override
    public List<BlogCommentCountDto> countNumberOfComments(List<Integer> blogIds) {
       return commentRepository.countNumberOfCommentsForBlogs(blogIds);
    }
}
