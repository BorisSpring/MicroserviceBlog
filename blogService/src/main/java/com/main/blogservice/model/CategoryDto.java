package com.main.blogservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class CategoryDto {

    private Integer id;
    private String name;
    private Integer order;
    private Long blogsCount;

}
