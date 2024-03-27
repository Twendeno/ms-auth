package com.twendeno.msauth.user.dto;

import jakarta.validation.constraints.NotBlank;

public record UserUpdateDto(
        @NotBlank
        String name
) {
    @Override
    public String name() {
        return name.trim();
    }
}
