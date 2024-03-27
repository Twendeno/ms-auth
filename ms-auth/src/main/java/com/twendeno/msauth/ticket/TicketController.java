package com.twendeno.msauth.ticket;

import com.twendeno.msauth.shared.model.ApiResponse;
import com.twendeno.msauth.ticket.dto.CreateTicketDto;
import com.twendeno.msauth.ticket.dto.UpdateTicketDto;
import com.twendeno.msauth.ticket.entity.Ticket;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RequestMapping("/tickets")
@RestController
public class TicketController {
    private final TicketService ticketService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiResponse<Ticket>> createTicket(@RequestBody CreateTicketDto ticketDto) {
        return new ResponseEntity<>(ticketService.save(ticketDto), HttpStatus.CREATED);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<List<Ticket>>> getTickets() {
        return ResponseEntity.ok(ticketService.findAll());
    }

    @GetMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<Ticket>> getTicket(@PathVariable("name") String name) {
        return ResponseEntity.ok(ticketService.findByName(name));
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<ApiResponse<String>> deleteTicket(@PathVariable("uuid") String uuid) {
        return ResponseEntity.ok(ticketService.deleteById(uuid));
    }

    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<Ticket>> getTicketById(@PathVariable("uuid") String uuid) {
        return ResponseEntity.ok(ticketService.findById(uuid));
    }

    @PutMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<Ticket>> updateTicket(@RequestBody UpdateTicketDto ticketDto, @PathVariable("uuid") String uuid) {
        return ResponseEntity.ok(ticketService.update(ticketDto, uuid));
    }
}
