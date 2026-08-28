package com.mind.octo.api.user.dto;

import java.time.Instant;

public record OctoUserResponse(
        Long id,
        String email,
        String displayName,
        Instant createdAt
) {
}