package com.twendeno.msauth.userLicense;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserLicenseRepository extends JpaRepository<UserLicense, UUID> {
    Optional<UserLicense> findByKey(String key);
}
