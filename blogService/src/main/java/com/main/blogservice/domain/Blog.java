package com.main.blogservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Blog")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Blog extends BaseEntity {

    @Builder
    public Blog(Integer id, LocalDateTime createdDate, LocalDateTime lastModified, Integer important, String title, String image, Integer views, String description, String contentBody, Category category, List<Tag> tags, Integer userId, boolean enabled) {
        super(id, createdDate, lastModified);
        this.important = important;
        this.title = title;
        this.image = image;
        this.views = views;
        this.description = description;
        this.contentBody = contentBody;
        this.category = category;
        this.tags = tags;
        this.userId = userId;
        this.enabled = enabled;
    }

    private Integer important;

    @NotNull(message="title required")
    @Size(min= 25, max= 250, message= "Title must be over 25 and less then 250 chars")
    @Column(nullable = false, columnDefinition = "varchar(250)")
    private String title;

    private String image;

    @PositiveOrZero
    private Integer views = 0;

    @NotNull(message="descriptino required")
    @Size(min=50, max= 500, message="Size must be over 50 and less then 500 chars")
    @Column(nullable = false, columnDefinition = "varchar(500)")
    private String description;

    @Size(min=50, max= 1000, message="Size must be over 50 and less then 1000 chars")
    @NotNull(message="Blog content body required")
    @Column(nullable = false, columnDefinition = "varchar(1000)")
    private String contentBody;

    @ManyToOne( cascade = {CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name="category_id")
    private Category category;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name="blog_tag",
            joinColumns = @JoinColumn(name="blog_id"),
            inverseJoinColumns = @JoinColumn(name="tag_id"))
    private List<Tag> tags = new ArrayList<>();

	@Column(nullable = false)
    private Integer userId;

    private boolean enabled;

}