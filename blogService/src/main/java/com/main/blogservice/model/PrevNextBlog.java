package com.main.blogservice.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrevNextBlog {

    private Integer id;
    private String title;
}
