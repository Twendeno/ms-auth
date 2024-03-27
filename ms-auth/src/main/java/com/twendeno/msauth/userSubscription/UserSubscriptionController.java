package com.twendeno.msauth.userSubscription;

import com.twendeno.msauth.shared.model.ApiResponse;
import com.twendeno.msauth.userSubscription.dto.ActivateUserSubscriptionDto;
import com.twendeno.msauth.userSubscription.dto.CreateUserSubscriptionDto;
import com.twendeno.msauth.userSubscription.entity.UserSubscription;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ApiResponse<UserSubscription>> save(@Valid @RequestBody CreateUserSubscriptionDto userSubscriptionDto) {
        return new ResponseEntity<>(this.userSubscriptionService.save(userSubscriptionDto), HttpStatus.CREATED);
    }


    @PostMapping(value = "/activate")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<String>> activateLicence(@Valid @RequestBody ActivateUserSubscriptionDto userSubscriptionDto) {
        return ResponseEntity.ok(this.userSubscriptionService.activateSubscription(userSubscriptionDto));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<List<UserSubscription>>> getUserLicense() {
        return ResponseEntity.ok(this.userSubscriptionService.getUserSubscription());
    }
}
