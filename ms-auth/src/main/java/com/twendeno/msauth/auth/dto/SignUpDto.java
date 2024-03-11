package com.twendeno.msauth.auth.dto;

import com.twendeno.msauth.role.Role;

public record SignUpDto(String email, String password, String name, Role role) {
}
