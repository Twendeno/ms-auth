package com.twendeno.msauth.subscription.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateSubscriptionDto(
        @NotBlank(message = "Name is required")
        String name,
        String description, float price, int duration) {
    @Override
    public String name() {
        return name.trim().toUpperCase();
    }
}
