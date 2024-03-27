package com.twendeno.msauth.heritage.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CreateHeritageDto(
        @NotBlank(message = "Business name is required")
        String businessName,
        @NotBlank(message = "User email is required")
        @Pattern(regexp = "(^[A-Za-z0-9+_.-]+@twendeno.com)|([^@ \\t\\r\\n]+@[^@ \\t\\r\\n]+\\.[^@ \\t\\r\\n]+)", message = "Invalid email")
        String userEmail,
        @NotBlank(message = "Matriculation is required")
        @Pattern(regexp = "^[0-9]{3}-[A-Z]{2}-[0-9]$", message = "Invalid matriculation")
        String matriculation,

        @NotBlank(message = "Brand is required")
        String brand,// marque
        @NotBlank(message = "Model is required")
        String model,
        @NotBlank(message = "Mileage is required")
        int mileage
) {
    @Override
    public String brand() {
        return brand.trim().toUpperCase();
    }
}
