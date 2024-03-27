package com.twendeno.msauth.validation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ValidationDto(
        @NotBlank(message = "Username is required")
        String username,
        @NotBlank(message = "Code is required")
        @Pattern(regexp = "^[0-9]{6}$", message = "Invalid code")
        String code
) {
    @Override
    public String code() {
        return code.trim();
    }
}
