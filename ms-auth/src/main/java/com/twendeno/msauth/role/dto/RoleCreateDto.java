package com.twendeno.msauth.role.dto;

public record RoleCreateDto(String name) {
    @Override
    public String name() {
        return name.toUpperCase();
    }
}
