package com.mind.octo.api.mind.dto;

import java.time.Instant;

public record MindResponse(
        Long id,
        String name,
        String description,
        String icon,
        String color,
        boolean archived,
        Instant createdAt,
        Instant updatedAt
) {
}