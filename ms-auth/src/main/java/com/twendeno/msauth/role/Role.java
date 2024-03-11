package com.twendeno.msauth.role;

import com.twendeno.msauth.model.AbstractEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Builder
@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Role extends AbstractEntity {

    @Enumerated(EnumType.STRING)
    private RoleType name;
}
