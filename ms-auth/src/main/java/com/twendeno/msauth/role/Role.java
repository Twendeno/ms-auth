package com.twendeno.msauth.role;

import com.twendeno.msauth.model.AbstractEntity;
import com.twendeno.msauth.privilege.Privilege;
import com.twendeno.msauth.user.User;
import jakarta.persistence.*;
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
public class Role extends AbstractEntity {

    @Enumerated(EnumType.STRING)
    private RoleType name;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "roles_privileges",
            joinColumns = @JoinColumn(name = "role_uuid", referencedColumnName = "uuid"),
            inverseJoinColumns = @JoinColumn(name = "privilege_uuid", referencedColumnName = "uuid")
    )
    private Collection<Privilege> privileges;

}
