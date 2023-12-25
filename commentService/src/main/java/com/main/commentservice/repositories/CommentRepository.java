package com.main.commentservice.repositories;

import com.main.commentservice.domain.Comment;
import com.main.commentservice.model.BlogCommentCountDto;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByBlogId(Integer blogId);

    @Query("SELECT new com.main.commentservice.model.BlogCommentCountDto(c.blogId , count(c.id)) FROM Comment c WHERE c.blogId IN :blogIds AND c.enabled = true GROUP BY c.blogId")
    List<BlogCommentCountDto> countNumberOfCommentsForBlogs(List<Integer> blogIds);

    @Query("Select c From Comment c WHERE (:status IS NULL OR c.enabled=:status) AND (:blogId IS NULL OR c.blogId=:blogId)")
    PageImpl<Comment> findComments(@Param("status") Boolean status,@Param("blogId") Integer blogId, PageRequest of);
}
