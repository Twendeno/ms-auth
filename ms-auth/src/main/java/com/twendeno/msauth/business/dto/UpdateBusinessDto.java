package com.twendeno.msauth.business.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateBusinessDto(
        @NotBlank @Email String email,
        @NotBlank String name,
        @NotBlank String address,
        @NotBlank String city,
        String SIRET,
        double latitude,
        double longitude

) {

    @Override
    public String name() {
        return name.trim().toLowerCase();
    }
}
