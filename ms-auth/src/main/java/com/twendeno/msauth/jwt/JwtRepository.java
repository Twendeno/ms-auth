package com.twendeno.msauth.jwt;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface JwtRepository extends CrudRepository<Jwt, UUID> {
    Optional<Jwt> findByValue(String value);
}
