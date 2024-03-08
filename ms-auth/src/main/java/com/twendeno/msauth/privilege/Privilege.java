package com.twendeno.msauth.privilege;

import com.twendeno.msauth.model.AbstractEntity;
import com.twendeno.msauth.role.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;


@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Privilege extends AbstractEntity {

    private String name;
    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;
}
