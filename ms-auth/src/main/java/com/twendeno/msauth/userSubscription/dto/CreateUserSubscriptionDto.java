package com.twendeno.msauth.userSubscription.dto;

public record CreateUserSubscriptionDto(String email, String subscriptionName) {
    @Override
    public String subscriptionName() {
        return subscriptionName.toUpperCase();
    }
}
