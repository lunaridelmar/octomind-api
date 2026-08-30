package com.mind.octo.api.mind.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateMindRequest(

        @NotBlank
        @Size(max = 100)
        String name,

        @Size(max = 1000)
        String description,

        @Size(max = 50)
        String icon,

        @Size(max = 50)
        String color

) {
}