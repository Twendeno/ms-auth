package com.twendeno.msauth.user.entity;


import com.twendeno.msauth.model.AbstractEntity;
import com.twendeno.msauth.role.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Builder
@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User extends AbstractEntity implements UserDetails {

    private String name;

    @Column(unique = true)
    private String email;
    private String password;
    private boolean isEnable = false;

    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_uuid", referencedColumnName = "uuid"),
            inverseJoinColumns = @JoinColumn(name = "role_uuid", referencedColumnName = "uuid")
    )
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.role.getName().getAuthorities();
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.isEnable;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isEnable;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.isEnable;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnable;
    }


}
