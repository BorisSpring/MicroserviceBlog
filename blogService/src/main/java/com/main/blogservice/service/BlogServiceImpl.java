package com.main.blogservice.service;

import com.main.blogservice.config.UploadDir;
import com.main.blogservice.domain.Blog;
import com.main.blogservice.domain.Category;
import com.main.blogservice.domain.Tag;
import com.main.blogservice.exceptions.BlogException;
import com.main.blogservice.exceptions.CategoryException;
import com.main.blogservice.exceptions.NotFoundException;
import com.main.blogservice.mappers.BlogMapper;
import com.main.blogservice.model.*;
import com.main.blogservice.repositories.BlogRepository;
import com.main.blogservice.repositories.CategoryRepository;
import com.main.blogservice.requests.CreateBlogRequest;
import com.main.blogservice.service.clients.CommentFeingClient;
import com.main.blogservice.service.clients.UserServiceFeingClient;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;
    private final BlogMapper blogMapper;
    private final UserServiceFeingClient userServiceFeingClient;
    private final CommentFeingClient commentFeingClient;
    private final CategoryRepository categoryRepository;
    private final TokenService tokenService;
    private final TagService tagService;
    private final UploadDir uploadDir;

    @Override
    public SingleBlogDto findSingleBlog(Integer blogId) throws IOException {
        Blog blog = findById(blogId);
        return  blogMapper.blogToSingleBlogDto(blog);
    }

    public List<MainPageBlogDto> addBlogImages(List<Blog> blogs){
        return  blogs.stream().map(blog -> {
            MainPageBlogDto mainPageBlogDto = blogMapper.blogToMainPageBlogDto(blog);
            ClassPathResource resource = new ClassPathResource("static/" + blog.getImage());
            try {
                mainPageBlogDto.setBlogImage(StreamUtils.copyToByteArray(resource.getInputStream()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return mainPageBlogDto;
        }).collect(Collectors.toList());
    }

    public List<MainPageBlogDto> addUserImages(List<MainPageBlogDto> mainPageBlogDtoList) throws IOException {

        ResponseEntity<HashMap<Integer, UserDto>> userImagesResponse = userServiceFeingClient.getImage(mainPageBlogDtoList
                                                                                    .stream()
                                                                                    .mapToInt(MainPageBlogDto::getUserId)
                                                                                    .boxed()
                                                                                    .toList());

        if(userImagesResponse.hasBody() && userImagesResponse.getBody() != null){
            HashMap<Integer, UserDto> userImages = userImagesResponse.getBody();
            mainPageBlogDtoList.forEach(mainPageBlogDto -> {
                mainPageBlogDto.setUserImage(userImages.get(mainPageBlogDto.getUserId()) == null ? null : userImages.get(mainPageBlogDto.getUserId()).getImage());
                mainPageBlogDto.setFirstName(userImages.get(mainPageBlogDto.getUserId()) == null ? null : userImages.get(mainPageBlogDto.getUserId()).getFirstName());
                mainPageBlogDto.setLastName(userImages.get(mainPageBlogDto.getUserId()) == null ? null : userImages.get(mainPageBlogDto.getUserId()).getLastName());
            });
        }
        return  mainPageBlogDtoList;
    }

    public List<MainPageBlogDto> addNumberOfComments(List<MainPageBlogDto> mainPageBlogDtoList){
        ResponseEntity<List<BlogCommentCountDto>> numberOfCommentsByBlogId = commentFeingClient.findNumberOfCommentsByBlogId(mainPageBlogDtoList
                                                                                                                        .stream()
                                                                                                                        .mapToInt(MainPageBlogDto::getId)
                                                                                                                        .boxed()
                                                                                                                        .toList());

        if(numberOfCommentsByBlogId.hasBody() && numberOfCommentsByBlogId.getBody() != null){
            List<BlogCommentCountDto> numberOfCommentsByBlogIdBody = numberOfCommentsByBlogId.getBody();
            mainPageBlogDtoList.forEach(mainPageBlogDto -> {
                         numberOfCommentsByBlogIdBody.stream()
                        .filter(dto -> dto.getBlogId().equals(mainPageBlogDto.getId()))
                        .findFirst()
                        .ifPresentOrElse(dto -> {
                            mainPageBlogDto.setNumberOfComments(dto.getNumberOfComments());
                        }, () -> {
                            mainPageBlogDto.setNumberOfComments(0L);
                        });
            });
        }else{
            mainPageBlogDtoList.forEach(mainPageBlogDto -> {
                mainPageBlogDto.setNumberOfComments(0L);
            });
        }
        return  mainPageBlogDtoList;
    }

    @Override
    public Page<MainPageBlogDto> findBlogs(int pageNumber, String categoryName, String tagName, String query, Integer userId) throws IOException {
        PageRequest pageRequest = PageRequest.of(0, 5);
        List<MainPageBlogDto> mainPageBlogDtos = addNumberOfComments(
                addUserImages(
                        addBlogImages
                                (blogRepository.findBlogs(categoryName, tagName, query, userId,pageRequest).getContent())));
        return new PageImpl<>(mainPageBlogDtos,pageRequest, mainPageBlogDtos.size());
    }

    @Override
    public List<MainPageBlogDto> find12Newest() throws IOException {
        return addNumberOfComments(
                     addUserImages(
                        addBlogImages(blogRepository.findTop12ByEnabledIsTrueOrderByCreatedDateDesc())));
    }

    @Override
    public List<MainPageBlogDto> findLastThreeImportant() throws IOException {
        return addNumberOfComments(
                    addUserImages(
                        addBlogImages(blogRepository.findTop3ByImportantIsNotNullAndEnabledIsTrueOrderByImportantDesc())));
    }

    @Override
    public List<LastThreeDto> find3Newest() {
        List<Blog> lastThreeBlog = blogRepository.findTop3ByEnabledIsTrueOrderByCreatedDateDesc();

        List<LastThreeDto> lastThreeDtos = new ArrayList<>(lastThreeBlog.stream()
                                                                        .map(blogMapper::blogToLastThreeDto)
                                                                        .toList());

        for (int i = 0; i < lastThreeBlog.size(); i++) {
            ClassPathResource classPathResource = new ClassPathResource("static/" + lastThreeBlog.get(i).getImage());
            LastThreeDto lastThreeDto = lastThreeDtos.get(i);
            try {
                lastThreeDto.setBlogImage(StreamUtils.copyToByteArray(classPathResource.getInputStream()));
                lastThreeDtos.set(i, lastThreeDto);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }



        ResponseEntity<List<BlogCommentCountDto>> numberOfCommentsByBlogId = commentFeingClient.findNumberOfCommentsByBlogId(lastThreeDtos
                                                                                            .stream()
                                                                                            .mapToInt(LastThreeDto::getId)
                                                                                            .boxed()
                                                                                            .toList());

        if(numberOfCommentsByBlogId.hasBody() && numberOfCommentsByBlogId.getBody() != null){
            List<BlogCommentCountDto> numberOfCommentsByBlogIdBody = numberOfCommentsByBlogId.getBody();
            lastThreeDtos.forEach(lastThreeDto -> {
                numberOfCommentsByBlogIdBody.stream()
                        .filter(dto -> dto.getBlogId().equals(lastThreeDto.getId()))
                        .findFirst()
                        .ifPresentOrElse(dto -> {
                            lastThreeDto.setNumberOfComments(dto.getNumberOfComments() == null ? 0 : dto.getNumberOfComments());
                        }, () -> {
                            lastThreeDto.setNumberOfComments(0L);
                        });
            });
        }else{
            lastThreeDtos.forEach(mainPageBlogDto -> {
                mainPageBlogDto.setNumberOfComments(0L);
            });
        }

        return lastThreeDtos;
    }

    @Override
    public Blog findById(Integer blogId) {
        return  blogRepository.findById(blogId)
                .orElseThrow(() -> new NotFoundException("Blog with id " + blogId  + " not found!"));
    }

    @Transactional
    @Override
    public void makeImportant(Integer blogId) {
        Blog blog = findById(blogId);
        Integer maxImportant = blogRepository.selectMaxImportant();
        blog.setImportant(maxImportant == null ? 1 : maxImportant + 1);

        blogRepository.save(blog);
    }


    @Transactional
    @Override
    public void makeUnImportant(Integer blogId) {
        Blog blog = findById(blogId);
        if (blog.getImportant() != null) {
            blog.setImportant(null);
            blogRepository.save(blog);
        }
    }

    @Override
    public void deleteBlog(Integer blogId) {
        if(!blogRepository.existsById(blogId))
            throw new BlogException("Blog with id " + blogId + " doesnt exists!");

        blogRepository.deleteById(blogId);
    }

    @Override
    public void enableBlog(Integer blogId) {
        Blog blog = findById(blogId);
        if(!blog.isEnabled()){
            blog.setEnabled(true);
            blogRepository.save(blog);
        }
    }

    @Transactional
    @Override
    public void disableBlog(Integer blogId) {
        Blog blog = findById(blogId);
        if(blog.isEnabled()){
            blog.setEnabled(false);
            blogRepository.save(blog);
        }
    }

    @Override
    public Page<BlogDto> findBlogsInfo(int page, String filterBy) {
         return blogRepository.findAll(PageRequest.of((page - 1), 15))
                                        .map(blogMapper::blogToBlogDto);
    }

    @Override
    public Blog craeteBlog(CreateBlogRequest request, String jwt) throws CategoryException, IOException {
        Blog blog;
        Category category;
        String imageName;

        if(request.getBlogId() != null){
            blog = findById(request.getBlogId());
        }else{
            blog = new Blog();
        }

        if(request.getBlogId() == null && request.getImage() == null){
            throw new BlogException("Image is required!");
        }

        if(request.getCategoryId() != null) {
            category = categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new CategoryException("Category with id " + request.getCategoryId() + " doesnt exist"));
            blog.setCategory(category);
        }

        if(request.getUserId() == null){
            blog.setUserId(tokenService.getUserIdFromToken(jwt));
        }

        blog.setDescription(request.getDescription());
        blog.setContentBody(request.getContentBody());
        blog.setTitle(request.getTitle());

        if(request.getTagsId() != null && request.getTagsId().isEmpty()){
            List<Tag> tags = new ArrayList<>();
            request.getTagsId().forEach(id -> {
                    tags.add(tagService.findById(id));

            });
            blog.setTags(tags);
        }

        Path path = Paths.get(uploadDir.uploadDir());

        if(!Files.exists(path))
            Files.createDirectories(path);

        if(request.getImage() != null){
            imageName = UUID.randomUUID() + request.getImage().getOriginalFilename();
            Files.copy(request.getImage().getInputStream(), path.resolve(imageName), StandardCopyOption.REPLACE_EXISTING);
            blog.setImage(imageName);
        }

       return blogRepository.saveAndFlush(blog);
    }


}
