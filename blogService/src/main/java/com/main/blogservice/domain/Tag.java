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
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Tag extends  BaseEntity {

    @Builder
    public Tag(Integer id, LocalDateTime createdDate, LocalDateTime lastModified, String name, List<Blog> blogs) {
        super(id, createdDate, lastModified);
        this.name = name;
        this.blogs = blogs;
    }

    @Column(nullable = false, columnDefinition = "varchar(45)")
    @NotNull
    @Size(min = 1, max = 45, message= "Size must be over 1 and les then 45 chars.")
    private String name;


    @JsonIgnore
    @ManyToMany
    @JoinTable(name="blog_tag",
            joinColumns = @JoinColumn(name="tag_id"),
            inverseJoinColumns = @JoinColumn(name="blog_id"))
    private List<Blog> blogs = new ArrayList<>();

}