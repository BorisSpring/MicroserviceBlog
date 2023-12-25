package com.main.commentservice.functions.events;

import com.main.commentservice.requests.CreateCommentRequest;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValidateBlogRequest {
   private CreateCommentRequest commentRequest;
}
