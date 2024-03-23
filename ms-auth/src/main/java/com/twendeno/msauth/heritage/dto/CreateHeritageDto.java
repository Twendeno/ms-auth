package com.twendeno.msauth.heritage.dto;

public record CreateHeritageDto(
        String businessName,
        String userEmail,
        String matriculation,
        String brand,
        String model,
        int mileage
) {
    @Override
    public String brand() {
        return brand.trim().toUpperCase();
    }
}
