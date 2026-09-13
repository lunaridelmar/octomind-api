package com.mind.octo.api.activity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record CreateActivityRequest(

        @NotBlank
        @Size(max = 200)
        String title,

        @NotEmpty
        Set<Long> mindIds
) {
}