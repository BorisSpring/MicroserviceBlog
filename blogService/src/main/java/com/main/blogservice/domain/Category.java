package com.main.blogservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="category")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Category extends BaseEntity {


    @Builder
    public Category(Integer id, LocalDateTime createdDate, LocalDateTime lastModified, String name, Integer order, List<Blog> blogs) {
        super(id, createdDate, lastModified);
        this.name = name;
        this.order = order;
        this.blogs = blogs;
    }

    @NotNull
    @Size(min = 1, max = 45, message= "Size must be over 1 and les then 45 chars.")
    @Column(columnDefinition = "varchar(50)", unique = true)
    private String name;

    @Column(name="categoryOrder", unique = true, columnDefinition = "tinyint")
    private Integer order;

    @OneToMany(mappedBy="category", cascade = {CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE, CascadeType.PERSIST})
    @JsonIgnore
    private List<Blog> blogs = new ArrayList<>();

}