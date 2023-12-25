package com.main.commentservice.requests;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
@AllArgsConstructor
public class CreateCommentRequest {

    @NotNull(message = "Blog id must not be empty!")
    @PositiveOrZero(message = "Blog id must be above zero!")
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
}
