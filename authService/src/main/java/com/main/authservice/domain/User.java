package com.main.authservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="Users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User extends BaseEntity {

    @Builder
    public User(Integer id, LocalDateTime createdDate, LocalDateTime lastModified, String firstName, String lastName, String email, String number, String image, String password, boolean enabled, Authority authority) {
        super(id, createdDate, lastModified);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.number = number;
        this.imageName = image;
        this.password = password;
        this.enabled = enabled;
        this.authority = authority;
    }

    @Column(columnDefinition = "varchar(50)", nullable = false)
    @NotNull
    @Size(min = 2, max = 50 , message = "Min size is 2 and max 50 chars")
    private String firstName;

    @Column(columnDefinition = "varchar(30)", nullable = false)
    @NotNull
    @Size(min = 2 , max = 30, message = "Min length is 2 char and max 30 chars")
    private String lastName;

    @Column(unique = true,nullable = false)
    @NotNull
    @Email
    private String email;

    @Pattern(regexp = "[0-9]+", message = "Number must contain only numbers")
    @Column(columnDefinition = "varchar(50)", unique = true, nullable = false)
    private String number;

    private String imageName;

    @JsonIgnore
    @NotNull
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean enabled;

    @ManyToOne( cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name="authority_id")
    private Authority authority;

}