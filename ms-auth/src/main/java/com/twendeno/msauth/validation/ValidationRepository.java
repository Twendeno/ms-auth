package com.twendeno.msauth.validation;

import org.springframework.data.repository.CrudRepository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface ValidationRepository extends CrudRepository<Validation, UUID> {
    Optional<Validation> findByCode(String code);

    void deleteAllByExpirationBefore(Instant now);
}
