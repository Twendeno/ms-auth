package com.twendeno.msauth.license;

import com.twendeno.msauth.shared.model.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class License extends AbstractEntity {
    @Column(unique = true)
    private String name;
    private float price = 0.0f; //in XAF
    private int duration= 1; //in months
}
