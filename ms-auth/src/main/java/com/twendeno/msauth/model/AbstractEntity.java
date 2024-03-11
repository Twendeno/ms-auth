package com.twendeno.msauth.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AbstractEntity implements Serializable {
    @Id
    @UuidGenerator
    private UUID uuid;

    @CreatedDate
    @Column(name = "createdAt", updatable = false, nullable = false)
    @JsonIgnore
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updatedAt", nullable = false)
    @JsonIgnore
    private Instant updatedAt;
}
