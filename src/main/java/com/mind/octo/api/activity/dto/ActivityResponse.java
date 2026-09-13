package com.mind.octo.api.activity.dto;

import java.time.Instant;
import java.util.Set;

public record ActivityResponse(
        Long id,
        String title,
        boolean completed,
        Set<Long> mindIds,
        Instant createdAt,
        Instant completedAt
) {
}