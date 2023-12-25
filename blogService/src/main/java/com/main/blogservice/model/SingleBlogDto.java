package com.main.blogservice.model;

import com.main.blogservice.domain.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SingleBlogDto {

    private List<CommentDto> comments;
    private String title;
    private String description;
    private String contentBody;
    private byte [] blogImage;
    private byte [] userImage;
    private Integer userId;
    private int views;
    private String firstName;
    private String lastName;
    private PrevNextBlog prev;
    private PrevNextBlog next;
    private String category;
    private LocalDateTime created;
    private List<Tag> tags  = new ArrayList<>();
}
