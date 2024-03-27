package com.twendeno.msauth.userLicense.dto;

import jakarta.validation.constraints.NotBlank;

public record UserLicenseDto(
        @NotBlank(message = "Business name is required")
        String businessName,
        @NotBlank(message = "License name is required")
        String licenseName) {
    @Override
    public String licenseName() {
        return licenseName.toUpperCase();
    }

    @Override
    public String businessName() {
        return businessName.trim().toLowerCase();
    }
}
