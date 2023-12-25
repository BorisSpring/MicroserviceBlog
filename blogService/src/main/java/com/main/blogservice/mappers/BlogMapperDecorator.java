package com.main.blogservice.mappers;

import com.main.blogservice.domain.Blog;
import com.main.blogservice.model.*;
import com.main.blogservice.repositories.BlogRepository;
import com.main.blogservice.service.clients.CommentFeingClient;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.util.List;

@Component
@NoArgsConstructor
public abstract class BlogMapperDecorator implements  BlogMapper  {

    @Autowired
    private  BlogMapper blogMapper;

    @Autowired
    private  BlogRepository blogRepository;

    @Autowired
    private CommentFeingClient commentFeingClient;

    public BlogMapperDecorator(BlogMapper blogMapper, BlogRepository blogRepository) {
        this.blogMapper = blogMapper;
        this.blogRepository = blogRepository;
    }

    @Override
    public MainPageBlogDto blogToMainPageBlogDto(Blog blog) {
        return blogMapper.blogToMainPageBlogDto(blog);
    }

    @Override
    public SingleBlogDto blogToSingleBlogDto(Blog blog) throws IOException {
        SingleBlogDto singleBlogDto = blogMapper.blogToSingleBlogDto(blog);
        singleBlogDto.setBlogImage(StreamUtils.copyToByteArray(new ClassPathResource("static/" + blog.getImage()).getInputStream()));
        singleBlogDto.setPrev(blogRepository.findPrev(blog.getId()));
        singleBlogDto.setNext(blogRepository.findNext(blog.getId()));
        ResponseEntity<List<CommentDto>> commentsByBlogId = commentFeingClient.findCommentsByBlogId(blog.getId());
        singleBlogDto.setComments(commentsByBlogId.hasBody() ? commentsByBlogId.getBody() : null);
        return singleBlogDto;
    }

    @Override
    public LastThreeDto blogToLastThreeDto(Blog blog) {
       return blogMapper.blogToLastThreeDto(blog);
    }

    @Override
    public BlogDto blogToBlogDto(Blog blog) {
        return blogMapper.blogToBlogDto(blog);
    }
}
