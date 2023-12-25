package com.main.blogservice.model;

import lombok.*;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Setter
@Getter
public class MainPageBlogDto {

    private String category;
    private String title;
    private String description;
    private int userId;
    private int id;
    private LocalDateTime created;
    private Long numberOfComments;
    private String firstName;
    private String lastName;
    private byte [] blogImage;
    private byte [] userImage;
}
