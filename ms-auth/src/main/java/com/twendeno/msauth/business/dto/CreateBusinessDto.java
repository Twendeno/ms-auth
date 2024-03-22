package com.twendeno.msauth.business.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateBusinessDto(
        @NotBlank @Email String email,
        @NotBlank String name,
        @NotBlank String address,
        @NotBlank String city,
        @NotBlank String country,
        @NotBlank String phone
) {

    @Override
    public String name() {
        return name.trim().toLowerCase();
    }

    @Override
    public String phone() {
        String cleanedPhone = this.phone.trim().toLowerCase().replaceAll("\\D", ""); // Remove all non-digit characters
        if (cleanedPhone.length() != 9) {
            throw new IllegalArgumentException("Phone number must have 10 digits.");
        }
        return cleanedPhone.substring(0, 2) + " " + cleanedPhone.substring(2, 5) + " " + cleanedPhone.substring(5, 7) + " " + cleanedPhone.substring(7);
    }


}
