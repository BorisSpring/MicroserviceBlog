package com.main.authservice.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="authority")
@NoArgsConstructor
@Setter
@Getter
@ToString(callSuper = true)
public class Authority extends BaseEntity {

    @Builder
    public Authority(Integer id, LocalDateTime createdDate, LocalDateTime lastModified, String authority) {
        super(id, createdDate, lastModified);
        this.authority = authority;
    }

    @NotNull
    @Column(unique = true, columnDefinition = "varchar(20)", nullable = false)
    private String authority;

    @OneToMany(mappedBy = "authority", cascade = CascadeType.ALL)
    private List<User> users;
}