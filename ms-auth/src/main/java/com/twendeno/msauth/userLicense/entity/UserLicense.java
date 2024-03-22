package com.twendeno.msauth.userLicense.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.twendeno.msauth.business.entity.Business;
import com.twendeno.msauth.license.License;
import com.twendeno.msauth.model.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Builder
@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UserLicense extends AbstractEntity {

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "business_uuid", referencedColumnName = "uuid", nullable = false)
    @JsonIgnore
    private Business business;


    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "license_uuid", referencedColumnName = "uuid", nullable = false)
    @JsonIgnore
    private License license;


    @Column(unique = true)
    private String key;
    private boolean active = false;
    private boolean expired = false;

    private Instant activation;
    private Instant expiration;
}
