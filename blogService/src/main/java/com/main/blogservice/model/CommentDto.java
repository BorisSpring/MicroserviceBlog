package com.main.blogservice.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDto {

    private Integer id;
    private Integer blogId;
    private String name;
    private String content;
    private LocalDateTime createdDate;
}
