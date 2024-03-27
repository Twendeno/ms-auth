package com.twendeno.msauth.license.dto;

import jakarta.validation.constraints.NotBlank;

public record LicenseDto(
        @NotBlank(message = "Name is required")
        String name,
        float price, int duration) {
}
