package com.main.blogservice.service;
import com.main.blogservice.domain.Blog;
import com.main.blogservice.exceptions.CategoryException;
import com.main.blogservice.model.BlogDto;
import com.main.blogservice.model.LastThreeDto;
import com.main.blogservice.model.MainPageBlogDto;
import com.main.blogservice.model.SingleBlogDto;
import com.main.blogservice.requests.CreateBlogRequest;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;

public interface BlogService {

    SingleBlogDto findSingleBlog(Integer blogId) throws IOException;

    Page<MainPageBlogDto> findBlogs(int pageNumber, String categoryName, String tagName, String query, Integer userId) throws IOException;

    List<MainPageBlogDto> find12Newest() throws IOException;

    List<MainPageBlogDto> findLastThreeImportant() throws IOException;

    List<LastThreeDto> find3Newest();

    Blog findById(Integer blogId);

    void makeImportant(Integer blogId);

    void makeUnImportant(Integer blogId);

    void deleteBlog(Integer blogId);

    void enableBlog(Integer blogId);

    void disableBlog(Integer blogId);


    Page<BlogDto> findBlogsInfo(int page, String filterBy);

    Blog craeteBlog(CreateBlogRequest createBlogRequest, String jwt) throws CategoryException, IOException;
}
