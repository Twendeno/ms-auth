package com.twendeno.msauth.ticketGenerate;

import com.twendeno.msauth.ticketGenerate.dto.ActivateTicketGenerateDto;
import com.twendeno.msauth.ticketGenerate.dto.CreateTicketGenerateDto;
import com.twendeno.msauth.ticketGenerate.entity.TicketGenerate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/ticket-generate")
public class TicketGenerateController {

    private final TicketGenerateService ticketGenerateService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN','ROLE_SELLER')")
    public void createTicketGenerate(@RequestBody CreateTicketGenerateDto ticketGenerateDto) {
        ticketGenerateService.save(ticketGenerateDto);
    }

    @PostMapping("/activate")
    @ResponseStatus(HttpStatus.OK)
    public TicketGenerate activateTicket(@RequestBody ActivateTicketGenerateDto activateTicketGenerateDto) {
        return ticketGenerateService.activateTicket(activateTicketGenerateDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TicketGenerate> getTicketGenerate() {
        return ticketGenerateService.getTicketGenerate();
    }

    @GetMapping("/{reference}")
    @ResponseStatus(HttpStatus.OK)
    public List<TicketGenerate> getTicketGenerateByRef(@PathVariable String reference) {
        return ticketGenerateService.getTicketsGenerateByRef(reference, false);
    }
}
