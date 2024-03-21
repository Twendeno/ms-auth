package com.twendeno.msauth.ticketGenerate;

import com.twendeno.msauth.ticketGenerate.entity.TicketGenerate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TicketGenerateRepository extends JpaRepository<TicketGenerate, UUID> {

    @Query("FROM TicketGenerate tg WHERE tg.reference= :reference AND tg.active = :active AND tg.expired = :expired")
    Optional<TicketGenerate> findByReferenceAndActiveAndExpire(String reference, boolean active, boolean expired);

    @Query("FROM TicketGenerate tg WHERE tg.reference= :reference AND tg.expired = :expired")
    Optional<List<TicketGenerate>> findManyByReferenceAndActiveAndExpire(String reference, boolean expired);

    Optional<List<TicketGenerate>> findManyByReference(String reference);

    Optional<TicketGenerate> findByReference(String reference);
}
