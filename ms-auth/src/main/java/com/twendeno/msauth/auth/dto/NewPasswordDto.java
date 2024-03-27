package com.twendeno.msauth.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record NewPasswordDto(
        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email", regexp = "[^@ \\t\\r\\n]+@[^@ \\t\\r\\n]+\\.[^@ \\t\\r\\n]+")
        String email,
        @NotBlank(message = "Password is required")
        @Size(min = 12, max = 255, message = "Password must be at least 12 characters")
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{12,}$", message = "Password must contain at least one digit, one lowercase, one uppercase, one special character and no whitespace")
        String password,

        @NotBlank(message = "Code is required")
        @Pattern(regexp = "^[0-9]{6}$", message = "Invalid code")
        String code) {
    @Override
    public String email() {
        return email.trim();
    }

    @Override
    public String code() {
        return code.trim();
    }

    @Override
    public String password() {
        return password.trim();
    }
}
