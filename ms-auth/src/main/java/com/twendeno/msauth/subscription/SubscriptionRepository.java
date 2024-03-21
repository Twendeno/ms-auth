package com.twendeno.msauth.subscription;

import com.twendeno.msauth.subscription.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {
    Optional<Subscription> findByName(String name);
}
