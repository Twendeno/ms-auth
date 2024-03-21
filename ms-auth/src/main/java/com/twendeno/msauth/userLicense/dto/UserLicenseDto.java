package com.twendeno.msauth.userLicense.dto;

public record UserLicenseDto(String email, String licenseName) {
    @Override
    public String licenseName() {
        return licenseName.toUpperCase();
    }
}
