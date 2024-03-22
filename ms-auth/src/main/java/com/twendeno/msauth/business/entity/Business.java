package com.twendeno.msauth.business.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.twendeno.msauth.model.AbstractEntity;
import com.twendeno.msauth.user.User;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Business extends AbstractEntity {

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE})
    @JoinColumn(name = "user_uuid")
    @JsonIgnore
    private User user;

    @Column(unique = true)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String bio;

    private String address;
    private String city;
    private String country = "Rep. Congo";

    @Column(unique = true)
    private String phone;

    private double latitude = 0.0;
    private double longitude = 0.0;

    @Column(unique = true)
    private String SIRET;

}
