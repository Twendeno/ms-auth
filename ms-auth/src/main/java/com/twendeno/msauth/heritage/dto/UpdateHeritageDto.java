package com.twendeno.msauth.heritage.dto;

import jakarta.validation.constraints.Pattern;

public record UpdateHeritageDto(

        @Pattern(regexp = "(^[A-Za-z0-9+_.-]+@twendeno.com)|([^@ \\t\\r\\n]+@[^@ \\t\\r\\n]+\\.[^@ \\t\\r\\n]+)", message = "Invalid email")
        String userEmail,
        @Pattern(regexp = "^[0-9]{3}-[A-Z]{2}-[0-9]$", message = "Invalid matriculation")
        String matriculation,
        String buyDate,
        String firstRegistration,
        int mileage,
        double price,
        double sellingPrice,
        double rentPrice,
        double deposit,
        String lastTechnicalInspection,
        String nextTechnicalInspection,
        String lastMaintenance,
        String nextMaintenance,
        String lastOilChange,
        String nextOilChange,
        String lastInsurance,
        String newInsurance,
        String brand,
        String model,
        String carType,
        double fiscalPower,
        String fuelType,
        String transmission,
        String boxType,
        int doors,
        int seats,
        int gears,
        double length,
        double width,
        double height
) {
    @Override
    public String carType() {
        return carType.toUpperCase().trim();
    }

    @Override
    public String fuelType() {
        return fuelType.toUpperCase().trim();
    }

    @Override
    public String transmission() {
        return transmission.toUpperCase().trim();
    }

    @Override
    public String boxType() {
        return boxType.toUpperCase().trim();
    }

}
