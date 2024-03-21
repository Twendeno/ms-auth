package com.twendeno.msauth.role;

import com.twendeno.msauth.privilege.PrivilegeType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public enum RoleType {
    SUPER_ADMIN(
            Set.of(
                    PrivilegeType.SUPER_ADMIN_CREATE,
                    PrivilegeType.SUPER_ADMIN_READ,
                    PrivilegeType.SUPER_ADMIN_UPDATE,
                    PrivilegeType.SUPER_ADMIN_DELETE
            )
    ),
    ADMIN(
            Set.of(
                    PrivilegeType.ADMIN_CREATE,
                    PrivilegeType.ADMIN_READ,
                    PrivilegeType.ADMIN_UPDATE,
                    PrivilegeType.ADMIN_DELETE
            )
    ),
    DEV(
            Set.of(
                    PrivilegeType.DEV_CREATE,
                    PrivilegeType.DEV_READ,
                    PrivilegeType.DEV_UPDATE,
                    PrivilegeType.DEV_DELETE
            )
    ),
    USER(
            Set.of(
                    PrivilegeType.USER_READ
            )
    ),
    SELLER(
            Set.of(
                    PrivilegeType.SELLER_CREATE,
                    PrivilegeType.SELLER_READ
            )
    ),
    TENANT(
            Set.of(
                    PrivilegeType.TENANT_CREATE
            )
    );

    @Getter
    private final Set<PrivilegeType> privileges;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> grantedAuthorities = this.getPrivileges().stream()
                .map(privilegeType -> new SimpleGrantedAuthority(privilegeType.name()))
                .collect(Collectors.toList());

        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return grantedAuthorities;
    }
}
