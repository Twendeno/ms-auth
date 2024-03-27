package com.twendeno.msauth.userSubscription.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ActivateUserSubscriptionDto(
        @NotBlank(message = "Reference is required")
        @Pattern(regexp = "^[a-zA-Z0-9]{10}$", message = "Invalid reference")
        String reference) {
    @Override
    public String reference() {
        return reference.trim();
    }
}
