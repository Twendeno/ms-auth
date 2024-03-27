package com.twendeno.msauth.refreshToken;

import com.twendeno.msauth.shared.model.AbstractEntity;
import jakarta.persistence.Entity;
import lombok.*;

import java.time.Instant;

@Setter
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken extends AbstractEntity {

    private boolean expired;
    private String value;
    private Instant creation;
    private Instant expiration;
}
