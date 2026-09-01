package com.mind.octo.api.mindcombination.dto;

import jakarta.validation.constraints.Min;

public record RandomMindCombinationRequest(

        @Min(value = 2, message = "At least two minds are required")
        int count

) {
}