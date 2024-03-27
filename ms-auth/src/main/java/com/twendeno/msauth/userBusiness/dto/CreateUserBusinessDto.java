package com.twendeno.msauth.userBusiness.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateUserBusinessDto(
        @NotBlank(message = "Username is required")
        String username,
        @NotBlank(message = "Business name is required")
        String businessName
) {
}
