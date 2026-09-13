package com.mind.octo.api.mindcombination.dto;

import java.time.Instant;

public record MindCombinationSuggestionResponse(
        Long id,
        String text,
        Instant createdAt
) {
}