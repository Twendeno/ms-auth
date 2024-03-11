package com.twendeno.msauth.privilege;

import com.twendeno.msauth.model.AbstractEntity;
import com.twendeno.msauth.role.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Privilege extends AbstractEntity {

    private String name;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "role_uuid", referencedColumnName = "uuid", nullable = false)
    private Role role;
}
