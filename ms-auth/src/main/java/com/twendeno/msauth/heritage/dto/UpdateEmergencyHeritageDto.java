package com.twendeno.msauth.heritage.dto;

public record UpdateEmergencyHeritageDto(
        boolean accident,
        boolean breakdown,
        boolean maintenance,
        boolean technicalInspection,
        boolean oilChange

) { }
