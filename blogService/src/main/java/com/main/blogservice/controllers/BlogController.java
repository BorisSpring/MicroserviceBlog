package com.main.blogservice.controllers;

import com.main.blogservice.config.JwtConst;
import com.main.blogservice.domain.Blog;
import com.main.blogservice.exceptions.CategoryException;
import com.main.blogservice.model.BlogDto;
import com.main.blogservice.model.LastThreeDto;
import com.main.blogservice.model.MainPageBlogDto;
import com.main.blogservice.model.SingleBlogDto;
import com.main.blogservice.requests.CreateBlogRequest;
import com.main.blogservice.service.BlogService;
import io.jsonwebtoken.Jwts;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/blogs")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @GetMapping
    public ResponseEntity<Page<MainPageBlogDto>> findAllBlogsHandler(@RequestParam(name="page", defaultValue="1") Integer page,
                                                                     @RequestParam(name="category", required=false) String categoryName ,
                                                                     @RequestParam(name="tag", required = false) String tagName,
                                                                     @RequestParam(name="query", required = false) String query,
                                                                     @RequestParam(name="userId", required = false) Integer userId) throws IOException {
        return ResponseEntity.ok(blogService.findBlogs(page, categoryName, tagName, query, userId));
    }

    @GetMapping("/threeNewest")
    public ResponseEntity<List<LastThreeDto>> findThreeNewest(){
        return ResponseEntity.ok(blogService.find3Newest());
    }

    @GetMapping("/lastThreeImportant")
    public ResponseEntity<List<MainPageBlogDto>> getLastThreeImportant() throws IOException {
        return ResponseEntity.ok(blogService.findLastThreeImportant());
    }

    @GetMapping("/newest")
    public ResponseEntity<List<MainPageBlogDto>> findNewestHandler() throws IOException {
        return ResponseEntity.ok(blogService.find12Newest());
    }

    @GetMapping("/allBlogs")
    public ResponseEntity<Page<BlogDto>> findAllBlogsDtoHandler(@Positive(message = "Must be greather then zero!") @RequestParam(name="page", defaultValue="1", required = false) int page,
                                                                @RequestParam(name="filterBy", required=false) String filterBy){
        return ResponseEntity.ok(blogService.findBlogsInfo(page, filterBy));
    }

    @GetMapping("/{blogId}")
    public ResponseEntity<SingleBlogDto> findBlogByIdHandler(@PathVariable(name = "blogId") Integer blogId) throws IOException {
        return ResponseEntity.ok(blogService.findSingleBlog(blogId));
    }

    @PostMapping
    public ResponseEntity<Blog> createBlogHandler(@Valid @ModelAttribute CreateBlogRequest createBlogRequest,
                                                  @RequestHeader(name = JwtConst.JwtHeader) String jwt) throws CategoryException, IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(blogService.craeteBlog(createBlogRequest, jwt));
    }


    @DeleteMapping("/{blogId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public  void deleteBlogHandler(@PathVariable(name = "blogId") Integer blogId){
        blogService.deleteBlog(blogId);
    }


    @PutMapping("/important/{blogId}")
    @ResponseStatus(HttpStatus.OK)
    public void importantBlogHandler(@PathVariable(name = "blogId") Integer blogId){
        blogService.makeImportant(blogId);
    }

    @PutMapping("/unimportant/{blogId}")
    @ResponseStatus(HttpStatus.OK)
    public void unImportantBlogHandler(@PathVariable(name = "blogId") Integer blogId){
        blogService.makeUnImportant(blogId);
    }

    @PutMapping("/enable/{blogId}")
    @ResponseStatus(HttpStatus.OK)
    public void enableBlogHandler(@PathVariable(name = "blogId") Integer blogId){
        blogService.enableBlog(blogId);
    }

    @PutMapping("/disable/{blogId}")
    @ResponseStatus(HttpStatus.OK)
    public void disableBlogHandler(@PathVariable(name = "blogId") Integer blogId){
        blogService.disableBlog(blogId);
    }
}