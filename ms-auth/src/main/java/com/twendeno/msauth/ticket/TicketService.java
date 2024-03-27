package com.twendeno.msauth.ticket;

import com.twendeno.msauth.advice.EntityNotFoundException;
import com.twendeno.msauth.shared.model.ApiResponse;
import com.twendeno.msauth.ticket.dto.CreateTicketDto;
import com.twendeno.msauth.ticket.dto.UpdateTicketDto;
import com.twendeno.msauth.ticket.entity.Ticket;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    public ApiResponse<Ticket> save(CreateTicketDto ticketDto) {
        Ticket ticket = Ticket.builder()
                .name(ticketDto.name())
                .description(ticketDto.description())
                .price(ticketDto.price())
                .duration(ticketDto.duration())
                .build();
        ticket = ticketRepository.save(ticket);

        return ApiResponse.<Ticket>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .code(HttpStatus.CREATED.value())
                .data(ticket)
                .message("Ticket created successfully")
                .build();
    }

    public ApiResponse<Ticket> findByName(String name) {
        Ticket ticket = ticketRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Ticket not found"));

        return ApiResponse.<Ticket>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .code(HttpStatus.OK.value())
                .data(ticket)
                .message("Ticket found successfully")
                .build();
    }

    public ApiResponse<Ticket> findById(String uuid) {
        Ticket ticket = ticketRepository.findById(UUID.fromString(uuid)).orElseThrow(() -> new EntityNotFoundException("Ticket not found"));

        return ApiResponse.<Ticket>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .code(HttpStatus.OK.value())
                .data(ticket)
                .message("Ticket found successfully")
                .build();
    }

    public ApiResponse<String> deleteById(String uuid) {
        Ticket ticket = findById(uuid).getData();
        ticketRepository.delete(ticket);

        return ApiResponse.<String>builder()
                .status(HttpStatus.NO_CONTENT.getReasonPhrase())
                .code(HttpStatus.NO_CONTENT.value())
                .data(null)
                .message("Ticket deleted successfully")
                .build();
    }

    public ApiResponse<List<Ticket>> findAll() {
        List<Ticket> tickets = ticketRepository.findAll();

        return ApiResponse.<List<Ticket>>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .code(HttpStatus.OK.value())
                .data(tickets)
                .message("Tickets found successfully")
                .build();
    }

    public ApiResponse<Ticket> update(UpdateTicketDto ticketDto, String uuid) {
        Ticket ticket = findById(uuid).getData();

        ticket.setName(ticketDto.name());
        ticket.setDescription(ticketDto.description());
        ticket.setPrice(ticketDto.price());
        ticket.setDuration(ticketDto.duration());
        ticket = ticketRepository.save(ticket);

        return ApiResponse.<Ticket>builder()
                .status(HttpStatus.OK.getReasonPhrase())
                .code(HttpStatus.OK.value())
                .data(ticket)
                .message("Ticket updated successfully")
                .build();
    }
}
