package com.twendeno.msauth.business.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CreateBusinessDto(
        @NotBlank(message = "Email is mandatory")
        @Email(message = "Email should be valid",regexp = "(^[A-Za-z0-9+_.-]+@twendeno.com)|([^@ \\t\\r\\n]+@[^@ \\t\\r\\n]+\\.[^@ \\t\\r\\n]+)")
        String email,
        @NotBlank(message = "Name is required")
        String name,
        @NotBlank(message = "Address is required")
        String address,
        @NotBlank(message = "City is required")
        String city,
        @NotBlank(message = "Country is required")
        String country,
        @NotBlank(message = "Phone is required")
        String phone
) {

    @Override
    public String name() {
        return name.trim().toLowerCase();
    }

    @Override
    public String phone() {
        String cleanedPhone = this.phone.trim().toLowerCase().replaceAll("\\D", ""); // Remove all non-digit characters
        if (cleanedPhone.length() != 9) {
            throw new IllegalArgumentException("Phone number must have 9 digits.");
        }
        return cleanedPhone.substring(0, 2) + " " + cleanedPhone.substring(2, 5) + " " + cleanedPhone.substring(5, 7) + " " + cleanedPhone.substring(7);
    }


}
