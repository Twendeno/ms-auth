package com.twendeno.msauth.subscription;

import com.twendeno.msauth.advice.EntityNotFoundException;
import com.twendeno.msauth.shared.model.ApiResponse;
import com.twendeno.msauth.subscription.dto.CreateSubscriptionDto;
import com.twendeno.msauth.subscription.dto.UpdateSubscriptionDto;
import com.twendeno.msauth.subscription.entity.Subscription;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Service
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    public ApiResponse<Subscription> createSubscription(CreateSubscriptionDto subscriptionDto) {

        Subscription subscription = Subscription.builder()
                .name(subscriptionDto.name())
                .description(subscriptionDto.description())
                .price(subscriptionDto.price())
                .duration(subscriptionDto.duration())
                .build();

        subscription = subscriptionRepository.save(subscription);

        return ApiResponse.<Subscription>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .code(HttpStatus.CREATED.value())
                .message("Subscription created successfully")
                .data(subscription)
                .build();
    }

    public ApiResponse<List<Subscription>> getSubscriptions() {
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        return ApiResponse.<List<Subscription>>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .code(HttpStatus.OK.value())
                .message("Subscriptions retrieved successfully")
                .data(subscriptions)
                .build();
    }

    public ApiResponse<Subscription> getSubscriptionById(String uuid) {
        Subscription subscription = subscriptionRepository.findById(UUID.fromString(uuid)).orElseThrow(() -> new EntityNotFoundException("Subscription not found"));
        return ApiResponse.<Subscription>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .code(HttpStatus.OK.value())
                .message("Subscription retrieved successfully")
                .data(subscription)
                .build();
    }

    public ApiResponse<String> deleteSubscription(String uuid) {
        Subscription subscription = getSubscriptionById(uuid).getData();

        subscriptionRepository.delete(subscription);

        return ApiResponse.<String>builder()
                .status(HttpStatus.NO_CONTENT.getReasonPhrase())
                .code(HttpStatus.NO_CONTENT.value())
                .message("Subscription deleted successfully")
                .data(null)
                .build();
    }

    public ApiResponse<Subscription> updateSubscription(String uuid, UpdateSubscriptionDto subscriptionDto) {
        Subscription subscription = getSubscriptionById(uuid).getData();

        subscription.setName(subscriptionDto.name());
        subscription.setDescription(subscriptionDto.description());
        subscription.setPrice(subscriptionDto.price());
        subscription.setDuration(subscriptionDto.duration());
        subscription = subscriptionRepository.save(subscription);

        return ApiResponse.<Subscription>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .code(HttpStatus.OK.value())
                .message("Subscription updated successfully")
                .data(subscription)
                .build();
    }
}
