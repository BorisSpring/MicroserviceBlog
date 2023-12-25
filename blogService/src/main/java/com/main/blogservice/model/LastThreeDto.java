package com.main.blogservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class LastThreeDto {

    private Integer id;
    private String title;
    private LocalDateTime created;
    private byte [] blogImage;
    private Integer numberOfViews;
    private Long numberOfComments;
}
