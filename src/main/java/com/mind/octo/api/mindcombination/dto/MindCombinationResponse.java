package com.mind.octo.api.mindcombination.dto;

import com.mind.octo.api.mind.dto.MindResponse;

import java.time.Instant;
import java.util.Set;

public record MindCombinationResponse(
        Long id,
        Set<MindResponse> minds,
        Instant createdAt
) {
}