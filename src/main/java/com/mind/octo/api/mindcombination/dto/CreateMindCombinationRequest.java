package com.mind.octo.api.mindcombination.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record CreateMindCombinationRequest(

        @NotEmpty
        @Size(min = 2, message = "At least two minds are required")
        Set<Long> mindIds

) {
}