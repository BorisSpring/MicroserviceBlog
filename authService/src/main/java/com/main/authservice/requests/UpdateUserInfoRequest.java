package com.main.authservice.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserInfoRequest {

    @NotNull(message = "User id required!")
    private Integer id;

    @NotNull(message = "First name required")
    private String firstName;

    @NotNull(message = "Last name required")
    private String lastName;

    @NotNull(message = "Number required")
    private String number;

    @NotNull(message = "Email required")
    @Email(message = "Email is required!")
    private String email;
}