package com.twendeno.msauth.auth.dto;

import com.twendeno.msauth.role.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SignUpDto(
        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email", regexp = "(^[A-Za-z0-9+_.-]+@twendeno.com)|([^@ \\t\\r\\n]+@[^@ \\t\\r\\n]+\\.[^@ \\t\\r\\n]+)")
        String email,
        @NotBlank
        @Size(min = 12, max = 255, message = "Password must be at least 12 characters")
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{12,}$", message = "Password must contain at least one digit, one lowercase, one uppercase, one special character and no whitespace")
        String password,

        @NotBlank(message = "Name is required")
        String name,
        Role role) {
}
