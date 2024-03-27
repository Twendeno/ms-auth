package com.twendeno.msauth.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record SignInDto(
        @NotBlank(message = "Username is required")
        String username,
        @NotBlank(message = "Password is required")
        String password
) {

    @Override
    public String username() {
        return username.trim();
    }

    @Override
    public String password() {
        return password.trim();
    }
}
