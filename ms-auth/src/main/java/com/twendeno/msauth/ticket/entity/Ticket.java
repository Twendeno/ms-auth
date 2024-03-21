package com.twendeno.msauth.ticket.entity;

import com.twendeno.msauth.model.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;


@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Ticket extends AbstractEntity {

    @Column(unique = true)
    private String name;
    private String description;
    private float price = 0.0f; //in XAF
    private int duration= 1; //in hours
}
