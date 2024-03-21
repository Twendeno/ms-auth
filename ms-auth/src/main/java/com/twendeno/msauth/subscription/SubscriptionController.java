package com.twendeno.msauth.subscription;

import com.twendeno.msauth.subscription.dto.CreateSubscriptionDto;
import com.twendeno.msauth.subscription.dto.UpdateSubscriptionDto;
import com.twendeno.msauth.subscription.entity.Subscription;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    public Subscription createSubscription(@RequestBody CreateSubscriptionDto subscriptionDto) {
        return subscriptionService.createSubscription(subscriptionDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Subscription> getSubscriptions() {
        return subscriptionService.getSubscriptions();
    }

    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public Subscription getSubscriptionById(@PathVariable("uuid") String uuid) {
        return subscriptionService.getSubscriptionById(uuid);
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSubscription(@PathVariable("uuid") String uuid) {
        subscriptionService.deleteSubscription(uuid);
    }

    @PutMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public Subscription updateSubscription(@PathVariable("uuid") String uuid, @RequestBody UpdateSubscriptionDto subscriptionDto) {
        return subscriptionService.updateSubscription(uuid, subscriptionDto);
    }
}
