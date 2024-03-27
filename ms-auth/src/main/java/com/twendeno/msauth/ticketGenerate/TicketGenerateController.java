package com.twendeno.msauth.ticketGenerate;

import com.twendeno.msauth.shared.model.ApiResponse;
import com.twendeno.msauth.ticketGenerate.dto.ActivateTicketGenerateDto;
import com.twendeno.msauth.ticketGenerate.dto.CreateTicketGenerateDto;
import com.twendeno.msauth.ticketGenerate.entity.TicketGenerate;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ApiResponse<List<TicketGenerate>>> createTicketGenerate(@Valid @RequestBody CreateTicketGenerateDto ticketGenerateDto) {
        return new ResponseEntity<>(ticketGenerateService.save(ticketGenerateDto), HttpStatus.CREATED);
    }

    @PostMapping("/activate")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<TicketGenerate>> activateTicket(@Valid @RequestBody ActivateTicketGenerateDto activateTicketGenerateDto) {
        return ResponseEntity.ok(ticketGenerateService.activateTicket(activateTicketGenerateDto));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<List<TicketGenerate>>> getTicketGenerate() {
        return ResponseEntity.ok(ticketGenerateService.getTicketGenerate());
    }

    @GetMapping("/{reference}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ApiResponse<List<TicketGenerate>>> getTicketGenerateByRef(@PathVariable String reference) {
        return ResponseEntity.ok(ticketGenerateService.getTicketsGenerateByRef(reference, false));
    }
}
