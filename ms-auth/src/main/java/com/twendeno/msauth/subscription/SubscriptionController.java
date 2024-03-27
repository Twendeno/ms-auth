package com.twendeno.msauth.subscription;

import com.twendeno.msauth.shared.model.ApiResponse;
import com.twendeno.msauth.subscription.dto.CreateSubscriptionDto;
import com.twendeno.msauth.subscription.dto.UpdateSubscriptionDto;
import com.twendeno.msauth.subscription.entity.Subscription;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiResponse<Subscription>> createSubscription(@Valid @RequestBody CreateSubscriptionDto subscriptionDto) {
        return new ResponseEntity<>(subscriptionService.createSubscription(subscriptionDto), HttpStatus.CREATED);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<List<Subscription>>> getSubscriptions() {
        return ResponseEntity.ok(subscriptionService.getSubscriptions());
    }

    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<Subscription>> getSubscriptionById(@PathVariable("uuid") String uuid) {
        return ResponseEntity.ok(subscriptionService.getSubscriptionById(uuid));
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<ApiResponse<String>> deleteSubscription(@PathVariable("uuid") String uuid) {
        return ResponseEntity.ok(subscriptionService.deleteSubscription(uuid));
    }

    @PutMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<Subscription>> updateSubscription(@PathVariable("uuid") String uuid, @RequestBody UpdateSubscriptionDto subscriptionDto) {
        return ResponseEntity.ok(subscriptionService.updateSubscription(uuid, subscriptionDto));
    }
}
