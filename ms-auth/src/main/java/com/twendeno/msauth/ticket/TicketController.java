package com.twendeno.msauth.ticket;

import com.twendeno.msauth.ticket.dto.CreateTicketDto;
import com.twendeno.msauth.ticket.dto.UpdateTicketDto;
import com.twendeno.msauth.ticket.entity.Ticket;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RequestMapping("/tickets")
@RestController
public class TicketController {
    private final TicketService ticketService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTicket(@RequestBody CreateTicketDto ticketDto) {
        ticketService.save(ticketDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Ticket> getTickets() {
        return ticketService.findAll();
    }

    @GetMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    public Ticket getTicket(@PathVariable("name") String name) {
        return ticketService.findByName(name);
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTicket(@PathVariable("uuid") String uuid) {
        ticketService.deleteById(uuid);
    }

    @GetMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public Ticket getTicketById(@PathVariable("uuid") String uuid) {
        return ticketService.findById(uuid);
    }

    @PutMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public Ticket updateTicket(@RequestBody UpdateTicketDto ticketDto, @PathVariable("uuid") String uuid) {
        return ticketService.update(ticketDto, uuid);
    }
}
