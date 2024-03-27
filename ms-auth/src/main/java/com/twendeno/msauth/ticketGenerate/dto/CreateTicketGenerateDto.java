package com.twendeno.msauth.ticketGenerate.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateTicketGenerateDto(

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email", regexp = "(^[A-Za-z0-9+_.-]+@twendeno.com)|([^@ \\t\\r\\n]+@[^@ \\t\\r\\n]+\\.[^@ \\t\\r\\n]+)")
        String email,
        String ticketName, int count) {
    @Override
    public String ticketName() {
        return ticketName.toUpperCase();
    }

    @Override
    public int count() {
        return Math.max(count, 1);
    }
}
