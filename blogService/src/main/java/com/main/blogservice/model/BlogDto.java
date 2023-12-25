package com.main.blogservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BlogDto {

    private Integer id;
    private Boolean enabled;
    private String title;
    private Integer important;
    private String categoryName;
}
