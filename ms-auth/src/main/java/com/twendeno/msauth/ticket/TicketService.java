package com.twendeno.msauth.ticket;

import com.twendeno.msauth.ticket.dto.CreateTicketDto;
import com.twendeno.msauth.ticket.dto.UpdateTicketDto;
import com.twendeno.msauth.ticket.entity.Ticket;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    public Ticket save(CreateTicketDto ticketDto) {
        Ticket ticket = Ticket.builder()
                .name(ticketDto.name())
                .description(ticketDto.description())
                .price(ticketDto.price())
                .duration(ticketDto.duration())
                .build();
        return ticketRepository.save(ticket);
    }
    public Ticket findByName(String name) {
        return ticketRepository.findByName(name).orElseThrow(() -> new RuntimeException("Ticket not found"));
    }
    public Ticket findById(String uuid) {
        return ticketRepository.findById(UUID.fromString(uuid)).orElse(null);
    }
    public void deleteById(String uuid) {
        ticketRepository.deleteById(UUID.fromString(uuid));
    }
    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    public Ticket update(UpdateTicketDto ticketDto, String uuid) {
        Ticket ticket = findById(uuid);
        ticket.setName(ticketDto.name());
        ticket.setDescription(ticketDto.description());
        ticket.setPrice(ticketDto.price());
        ticket.setDuration(ticketDto.duration());
        return ticketRepository.save(ticket);
    }
}
