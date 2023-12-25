package com.main.slideservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="slide")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Slide extends  BaseEntity {

    @Builder
    public Slide(Integer id, LocalDateTime createdDate, LocalDateTime lastModified, String image, String title, String buttonTitle, String buttonUrl, boolean enabled, Integer orderNumber) {
        super(id, createdDate, lastModified);
        this.imageName = image;
        this.title = title;
        this.buttonTitle = buttonTitle;
        this.buttonUrl = buttonUrl;
        this.enabled = enabled;
        this.orderNumber = orderNumber;
    }

    @Column(nullable = false)
    @NotNull(message = "Image required")
    private String imageName;

    @Column(nullable = false, columnDefinition = "varchar(100)")
    @Size(min = 5 , max = 100, message = "Min lenght is 5  char and max 50 chars!")
    @NotNull(message = "Title required")
    private String title;

    @Column(nullable = false, columnDefinition = "varchar(100)")
    @Size(min = 5, max = 30, message = "Min size is 5 and max 30 chars")
    @NotNull(message = "Button Title required")
    private String buttonTitle;

    @Column(nullable = false)
    @NotNull(message = "Button url required!")
    @NotNull(message = "Button url required")
    private String buttonUrl;

    @Column(nullable = false)
    private boolean enabled;

    private Integer orderNumber;


}