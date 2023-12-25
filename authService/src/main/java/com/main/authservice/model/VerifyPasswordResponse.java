package com.main.authservice.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class VerifyPasswordResponse {

    private boolean isValid;
}
