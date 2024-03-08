package com.twendeno.msauth.jwt;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public interface JwtRepository extends CrudRepository<Jwt, UUID> {
    Optional<Jwt> findByValueAndDisableAndExpired(String value, boolean disable, boolean expired);

    @Query("FROM Jwt j WHERE j.user.email = :email AND j.disable = :disable AND j.expired = :expired")
    Optional<Jwt> findUserValidToken(String email, boolean disable, boolean expired);

    @Query("FROM Jwt j WHERE j.user.email = :email ")
    Stream<Jwt> findUser(String email);

    void deleteAllByExpiredAndDisable(boolean expired, boolean disable);
}
