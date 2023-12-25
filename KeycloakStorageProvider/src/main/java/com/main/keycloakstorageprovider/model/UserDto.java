package com.main.keycloakstorageprovider.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Integer id;

    private LocalDateTime createdDate;

    private LocalDateTime lastModified;

    private String firstName;

    private String lastName;

    private String email;

    private String number;
    private byte [] image;
    private boolean enabled;
}
