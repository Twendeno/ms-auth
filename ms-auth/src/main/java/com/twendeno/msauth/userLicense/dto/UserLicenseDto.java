package com.twendeno.msauth.userLicense.dto;

public record UserLicenseDto(String businessName, String licenseName) {
    @Override
    public String licenseName() {
        return licenseName.toUpperCase();
    }

    @Override
    public String businessName() {
        return businessName.trim().toLowerCase();
    }
}
