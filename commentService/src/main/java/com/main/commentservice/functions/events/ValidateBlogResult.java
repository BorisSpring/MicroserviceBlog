package com.main.commentservice.functions.events;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValidateBlogResult {

    private Integer blogId;
    private boolean isValid;
}
