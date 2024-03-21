package com.twendeno.msauth.subscription.dto;

public record CreateSubscriptionDto(String name, String description, float price, int duration) {
    @Override
    public String name() {
        return name.trim().toUpperCase();
    }
}
