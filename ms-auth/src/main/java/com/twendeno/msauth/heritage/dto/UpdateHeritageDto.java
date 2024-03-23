package com.twendeno.msauth.heritage.dto;

public record UpdateHeritageDto(
        String userEmail,
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
