package com.twendeno.msauth.ticket.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateTicketDto(
        @NotBlank(message = "Name is required")
        String name,
        String description, float price, int duration) {
    @Override
    public String name() {
        return name.trim().toUpperCase();
    }
}
