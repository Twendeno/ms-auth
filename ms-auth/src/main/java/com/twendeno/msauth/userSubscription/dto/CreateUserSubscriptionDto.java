package com.twendeno.msauth.userSubscription.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUserSubscriptionDto(
        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email", regexp = "(^[A-Za-z0-9+_.-]+@twendeno.com)|([^@ \\t\\r\\n]+@[^@ \\t\\r\\n]+\\.[^@ \\t\\r\\n]+)")
        String email,
        @NotBlank(message = "Subscription name is required")
        String subscriptionName) {
    @Override
    public String subscriptionName() {
        return subscriptionName.toUpperCase();
    }
}
