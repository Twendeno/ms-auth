package com.twendeno.msauth.heritage;

import com.twendeno.msauth.heritage.entity.Heritage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface HeritageRepository extends JpaRepository<Heritage, UUID> {
    Optional<Heritage> findByReference(String reference);
}
