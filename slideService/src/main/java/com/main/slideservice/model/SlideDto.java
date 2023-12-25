package com.main.slideservice.model;



import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SlideDto {

    private Integer id;

    private LocalDateTime createdDate;

    private LocalDateTime lastModified;
    private byte [] image;

    private String title;

    private String buttonTitle;

    private String buttonUrl;

    private boolean enabled;

    private Integer orderNumber;
}
