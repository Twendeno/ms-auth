package com.twendeno.msauth.privilege;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PrivilegeType {

    SUPER_ADMIN_CREATE,
    SUPER_ADMIN_READ,
    SUPER_ADMIN_UPDATE,
    SUPER_ADMIN_DELETE,

    ADMIN_CREATE,
    ADMIN_READ,
    ADMIN_UPDATE,
    ADMIN_DELETE,

    TENANT_CREATE,

    DEV_CREATE,
    DEV_READ,
    DEV_UPDATE,
    DEV_DELETE,

    SELLER_CREATE,
    SELLER_READ,

    DRIVER_CREATE,
    DRIVER_READ,

    USER_READ;


    private String privilegeName;
}
