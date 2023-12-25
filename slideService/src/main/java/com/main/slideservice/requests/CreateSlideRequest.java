package com.main.slideservice.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
public class CreateSlideRequest {

    @NotNull
    @Size(min = 10 , max = 50, message = "Min length is 10 char and max 50 chars!")
    private String buttonTitle;

    private Integer slideId;
    @NotNull
    @Size(min = 10 , max = 50, message = "Min length is 10 char and max 50 chars!")
    private String title;

    @NotNull
    @Size(min = 10 , max = 50, message = "Min length is 10  char and max 50 chars!")
    private String buttonUrl;

    private MultipartFile image;
}