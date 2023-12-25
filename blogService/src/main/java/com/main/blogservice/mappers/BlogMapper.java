package com.main.blogservice.mappers;

import com.main.blogservice.domain.Blog;
import com.main.blogservice.model.BlogDto;
import com.main.blogservice.model.LastThreeDto;
import com.main.blogservice.model.MainPageBlogDto;
import com.main.blogservice.model.SingleBlogDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.io.IOException;

@Mapper
@DecoratedWith(BlogMapperDecorator.class)
public interface BlogMapper {

    @Mapping(source = "createdDate", target = "created")
    @Mapping(source = "category.name", target = "category")
    MainPageBlogDto blogToMainPageBlogDto(Blog blog);

    @Mapping(source = "category.name", target = "category")
    SingleBlogDto blogToSingleBlogDto(Blog blog) throws IOException;

    @Mapping(source = "views", target="numberOfViews")
    LastThreeDto blogToLastThreeDto(Blog blog);

    @Mapping(source = "category.name", target = "categoryName")
    BlogDto blogToBlogDto(Blog blog);
}
