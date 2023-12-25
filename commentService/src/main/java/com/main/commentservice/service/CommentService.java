package com.main.commentservice.service;

import com.main.commentservice.domain.Comment;
import com.main.commentservice.exceptions.CommentException;
import com.main.commentservice.model.BlogCommentCountDto;
import com.main.commentservice.model.CommentDto;
import com.main.commentservice.requests.CreateCommentRequest;
import org.springframework.data.domain.PageImpl;

import java.util.List;


public interface CommentService {

    Comment addComment(CreateCommentRequest request) throws CommentException;

    void disableComment(Integer commentId) throws CommentException;

    void enableComment(Integer commentId) throws CommentException;

    void deleteComment(Integer commentId) throws CommentException;

    Comment findById(Integer commentId) throws CommentException;

    PageImpl<Comment> findComments(int pageNumber, Boolean status, Integer blogId);

    List<CommentDto> findByBlogId(Integer blogId);

    List<BlogCommentCountDto> countNumberOfComments(List<Integer> blogIds);
}
