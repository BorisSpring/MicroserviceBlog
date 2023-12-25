package com.main.blogservice.repositories;

import com.main.blogservice.domain.Blog;
import com.main.blogservice.model.PrevNextBlog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Integer> {

    @Query("Select b from Blog b " +
           "LEFT JOIN  b.category c " +
           "LEFT JOIN b.tags t " +
           "WHERE (:categoryName IS NULL or c.name =:categoryName) AND " +
           "(:tagName IS NULL or :tagName IN (SELECT tag.name FROM b.tags tag)) AND " +
           "(:query IS NULL OR b.title LIKE %:query% OR b.contentBody LIKE %:query% OR b.description LIKE %:query%) AND " +
           "(:userId IS NULL or b.userId =:userId) AND " +
            "b.enabled=true ")
    Page<Blog> findBlogs(@Param("categoryName") String categoryName,@Param("tagName") String tagName,@Param("query")  String query,@Param("userId")  Integer userId, PageRequest of);


    List<Blog> findTop12ByEnabledIsTrueOrderByCreatedDateDesc();

    List<Blog> findTop3ByImportantIsNotNullAndEnabledIsTrueOrderByImportantDesc();

    List<Blog> findTop3ByEnabledIsTrueOrderByCreatedDateDesc();

    @Query("Select MAX(b.important) FROM Blog b")
    Integer selectMaxImportant();

    @Query("SELECT new com.main.blogservice.model.PrevNextBlog(b.id , b.title) FROM Blog b WHERE b.id < :blogId ORDER BY b.id DESC LIMIT  1")
    PrevNextBlog findPrev(@Param("blogId")Integer blogId );

    @Query("SELECT NEW com.main.blogservice.model.PrevNextBlog(b.id, b.title) FROM Blog b WHERE b.id > :blogId ORDER BY b.id ASC LIMIT 1")
    PrevNextBlog findNext(@Param("blogId") Integer blogId );
}
