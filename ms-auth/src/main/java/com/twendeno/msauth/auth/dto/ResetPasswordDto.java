package com.twendeno.msauth.auth.dto;

import jakarta.validation.constraints.Email;

public record ResetPasswordDto(
        @Email(message = "Invalid email", regexp = "[^@ \\t\\r\\n]+@[^@ \\t\\r\\n]+\\.[^@ \\t\\r\\n]+")
        String email
) {
    @Override
    public String email() {
        return email.trim();
    }
}
