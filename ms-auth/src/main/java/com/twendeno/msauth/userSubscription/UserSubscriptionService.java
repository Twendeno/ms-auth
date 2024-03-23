package com.twendeno.msauth.userSubscription;

import com.twendeno.msauth.shared.Utils;
import com.twendeno.msauth.subscription.SubscriptionRepository;
import com.twendeno.msauth.subscription.entity.Subscription;
import com.twendeno.msauth.user.entity.User;
import com.twendeno.msauth.user.UserRepository;
import com.twendeno.msauth.userSubscription.dto.ActivateUserSubscriptionDto;
import com.twendeno.msauth.userSubscription.dto.CreateUserSubscriptionDto;
import com.twendeno.msauth.userSubscription.entity.UserSubscription;
import com.twendeno.msauth.validation.NotificationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class UserSubscriptionService {
    private final UserSubscriptionRepository userSubscriptionRepository;
    private final SubscriptionRepository subscriptionRepository;

    private final UserRepository userRepository;
    private final NotificationService notificationService;


    public void save(CreateUserSubscriptionDto userSubscriptionDto) {
        // Check if the user exists
        User user = this.userRepository.findByEmail(userSubscriptionDto.email()).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Check if the subscription exists
        Subscription subscription = this.subscriptionRepository.findByName(userSubscriptionDto.subscriptionName()).orElseThrow(() -> new RuntimeException("Subscription not found"));

        // Generate subscription reference
        String subscriptionReference = Utils.generateSubscriptionReference();

        UserSubscription userSubscription = UserSubscription.builder()
                .user(user)
                .subscription(subscription)
                .reference(subscriptionReference)
                .build();

        UserSubscription saved = this.userSubscriptionRepository.save(userSubscription);

        // Send notification
        this.notificationService.sendMailForSubscriptionWithAttachment(user,subscription);
    }

    public List<UserSubscription> getUserSubscription() {
        return this.userSubscriptionRepository.findAll();
    }

    public void activateSubscription(ActivateUserSubscriptionDto activateUserSubscriptionDto) {

        UserSubscription userSubscription = this.userSubscriptionRepository.findByReference(activateUserSubscriptionDto.reference()).orElseThrow(() -> new RuntimeException("Subscription not found"));

        // get subscription
        Subscription subscription = userSubscription.getSubscription();

        if (userSubscription.getExpiration() != null) {

            Date expirationDate = Date.from(userSubscription.getExpiration());
            boolean before = expirationDate.before(new Date());

            if (before) {
                userSubscription.setExpired(true);
                this.userSubscriptionRepository.save(userSubscription);
                return;
            }
        }

        // set active
        userSubscription.setActive(true);

        if (userSubscription.getExpiration() == null || userSubscription.getActivation() == null) {
            // set creation
            Instant creation = Instant.now();
            userSubscription.setActivation(creation);

            // set expiration
            LocalDateTime localDateTime = LocalDateTime.ofInstant(creation, ZoneId.systemDefault());
            localDateTime = localDateTime.plusMonths(subscription.getDuration());

            Instant expiration = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
            userSubscription.setExpiration(expiration);
        }

        this.userSubscriptionRepository.save(userSubscription);
    }
}
