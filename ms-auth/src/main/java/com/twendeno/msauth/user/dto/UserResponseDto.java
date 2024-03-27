package com.twendeno.msauth.user.dto;

import com.twendeno.msauth.role.Role;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public record UserResponseDto(
        String uuid,
        String name,
        String email,
        String username,
        boolean enable,
        boolean accountNonExpired,
        boolean credentialsNonExpired,
        boolean enabled,
        Role role,
        Collection<? extends GrantedAuthority> authorities,
        String createdAt,
        String updatedAt
) {
}
