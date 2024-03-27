package com.twendeno.msauth.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenDto(
        @NotBlank(message = "Refresh token is required")
        String refreshToken
) {
    @Override
    public String refreshToken() {
        return refreshToken.trim();
    }
}
