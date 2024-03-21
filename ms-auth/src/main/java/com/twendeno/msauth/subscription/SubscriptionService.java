package com.twendeno.msauth.subscription;

import com.twendeno.msauth.subscription.dto.CreateSubscriptionDto;
import com.twendeno.msauth.subscription.dto.UpdateSubscriptionDto;
import com.twendeno.msauth.subscription.entity.Subscription;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Service
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    public Subscription createSubscription(CreateSubscriptionDto subscriptionDto) {
        Subscription subscription = Subscription.builder()
                .name(subscriptionDto.name())
                .description(subscriptionDto.description())
                .price(subscriptionDto.price())
                .duration(subscriptionDto.duration())
                .build();
        return subscriptionRepository.save(subscription);
    }

    public List<Subscription> getSubscriptions() {
        return subscriptionRepository.findAll();
    }

    public Subscription getSubscriptionById(String uuid) {
        return subscriptionRepository.findById(UUID.fromString(uuid)).orElseThrow(() -> new RuntimeException("Subscription not found"));
    }

    public void deleteSubscription(String uuid) {
        subscriptionRepository.deleteById(UUID.fromString(uuid));
    }

    public Subscription updateSubscription(String uuid, UpdateSubscriptionDto subscriptionDto) {
        Subscription subscription = getSubscriptionById(uuid);
        subscription.setName(subscriptionDto.name());
        subscription.setDescription(subscriptionDto.description());
        subscription.setPrice(subscriptionDto.price());
        subscription.setDuration(subscriptionDto.duration());
        return subscriptionRepository.save(subscription);
    }
}
