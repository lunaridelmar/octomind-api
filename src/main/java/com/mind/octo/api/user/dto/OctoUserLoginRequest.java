package com.mind.octo.api.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record OctoUserLoginRequest(

        @NotBlank
        @Email
        String email,

        @NotBlank
        String password

) {
}