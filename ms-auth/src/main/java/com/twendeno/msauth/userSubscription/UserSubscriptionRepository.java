package com.twendeno.msauth.userSubscription;

import com.twendeno.msauth.userSubscription.entity.UserSubscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, UUID> {
    Optional<UserSubscription> findByReference(String reference);
}
