package com.twendeno.msauth.user;


import com.twendeno.msauth.model.AbstractEntity;
import com.twendeno.msauth.role.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

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

    @ManyToMany(cascade = {CascadeType.ALL, CascadeType.DETACH}, fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_uuid", referencedColumnName = "uuid"),
            inverseJoinColumns = @JoinColumn(name = "role_uuid", referencedColumnName = "uuid")
    )
    private Collection<Role> roles = new ArrayList<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().toString()))
                .collect(Collectors.toList());
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
