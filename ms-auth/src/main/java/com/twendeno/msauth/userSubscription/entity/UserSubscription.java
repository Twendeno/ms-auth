package com.twendeno.msauth.userSubscription.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.twendeno.msauth.license.License;
import com.twendeno.msauth.model.AbstractEntity;
import com.twendeno.msauth.subscription.entity.Subscription;
import com.twendeno.msauth.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserSubscription extends AbstractEntity {

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "user_uuid", referencedColumnName = "uuid", nullable = false)
    @JsonIgnore
    private User user;


    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "subscription_uuid", referencedColumnName = "uuid", nullable = false)
    @JsonIgnore
    private Subscription subscription;


    @Column(unique = true)
    private String reference;
    private boolean active = false;
    private boolean expired = false;

    private Instant activation;
    private Instant expiration;
}
