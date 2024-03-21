package com.twendeno.msauth.userSubscription.dto;

public record ActivateUserSubscriptionDto(String reference) {
    @Override
    public String reference() {
        return reference.trim();
    }
}
