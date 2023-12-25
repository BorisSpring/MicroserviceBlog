package com.main.blogservice.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@AllArgsConstructor
public class ErrorResponseDto {

    private String msg;
    private LocalDateTime timestamp;
    private String description;

}
