package com.main.commentservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@Entity
@NoArgsConstructor
@ToString(callSuper = true)
public class Comment extends BaseEntity {

    @Builder
    public Comment(Integer id, LocalDateTime createdDate, LocalDateTime lastModified, Integer blogId, String name, String email, String content, boolean enabled) {
        super(id, createdDate, lastModified);
        this.blogId = blogId;
        this.name = name;
        this.email = email;
        this.content = content;
        this.enabled = enabled;
    }

    @Column(nullable = false)
    private Integer blogId;

    @NotNull(message="Name required")
    @Size(min = 3 , max = 20 , message = "Minimum lenght is 3 maximum length is 20 chars")
    @Column(updatable = false, nullable = false, columnDefinition = "varchar(20)")
    private String name;

    @NotNull(message="Email required")
    @Email
    @Column(nullable = false, updatable = false)
    private String email;

    @Column(nullable = false, updatable = false)
    @NotNull(message="Comment content required")
    private String content;

    @Column(nullable = false)
    private boolean enabled;

}
