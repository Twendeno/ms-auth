package com.twendeno.msauth.ticketGenerate.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.twendeno.msauth.model.AbstractEntity;
import com.twendeno.msauth.ticket.entity.Ticket;
import com.twendeno.msauth.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.Instant;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TicketGenerate extends AbstractEntity {

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "user_uuid", referencedColumnName = "uuid", nullable = false)
    @JsonIgnore
    private User user;


    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "ticket_uuid", referencedColumnName = "uuid", nullable = false)
    @JsonIgnore
    private Ticket ticket;

    private String reference;
    private boolean active = false;
    private boolean expired = false;

    private Instant activation;
    private Instant expiration;
}
