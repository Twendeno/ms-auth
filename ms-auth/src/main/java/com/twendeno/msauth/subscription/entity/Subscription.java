package com.twendeno.msauth.subscription.entity;

import com.twendeno.msauth.model.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Subscription extends AbstractEntity {

    @Column(unique = true)
    private String name;
    private String description;
    private float price = 0.0f; //in XAF
    private int duration= 1; //in Months
}
