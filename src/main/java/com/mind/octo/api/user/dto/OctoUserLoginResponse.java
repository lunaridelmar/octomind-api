package com.mind.octo.api.user.dto;

public record OctoUserLoginResponse(
        String token,
        OctoUserResponse user
) {
}