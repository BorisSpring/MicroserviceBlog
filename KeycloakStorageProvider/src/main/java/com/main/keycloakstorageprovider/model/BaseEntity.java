package com.main.keycloakstorageprovider.model;


import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class BaseEntity {

    private Integer id;

    private LocalDateTime createdDate;

    private LocalDateTime lastModified;
}
