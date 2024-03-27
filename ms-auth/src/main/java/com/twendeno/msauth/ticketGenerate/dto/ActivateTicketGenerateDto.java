package com.twendeno.msauth.ticketGenerate.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ActivateTicketGenerateDto(
        @NotBlank(message = "Reference is required")
        @Pattern(regexp = "^[a-zA-Z0-9]{12}$", message = "Reference must be 12 characters long")
        String reference,
        String uuid) {
    @Override
    public String reference() {
        return reference.trim();
    }
}
