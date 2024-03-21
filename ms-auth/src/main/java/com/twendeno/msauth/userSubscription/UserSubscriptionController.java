package com.twendeno.msauth.userSubscription;

import com.twendeno.msauth.userSubscription.dto.ActivateUserSubscriptionDto;
import com.twendeno.msauth.userSubscription.dto.CreateUserSubscriptionDto;
import com.twendeno.msauth.userSubscription.entity.UserSubscription;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RequestMapping("user-subscription")
@RestController
public class UserSubscriptionController {

    private final UserSubscriptionService userSubscriptionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody CreateUserSubscriptionDto userSubscriptionDto) {
        this.userSubscriptionService.save(userSubscriptionDto);
    }


    @PostMapping(value = "/activate")
    @ResponseStatus(HttpStatus.OK)
    public void activateLicence(@RequestBody ActivateUserSubscriptionDto userSubscriptionDto) {
        this.userSubscriptionService.activateSubscription(userSubscriptionDto);
    }

    @GetMapping
    public List<UserSubscription> getUserLicense() {
        return this.userSubscriptionService.getUserSubscription();
    }
}
