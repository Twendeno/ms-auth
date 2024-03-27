package com.twendeno.msauth.ticketGenerate;

import com.twendeno.msauth.advice.EntityNotFoundException;
import com.twendeno.msauth.shared.Utils;
import com.twendeno.msauth.shared.model.ApiResponse;
import com.twendeno.msauth.ticket.TicketRepository;
import com.twendeno.msauth.ticket.entity.Ticket;
import com.twendeno.msauth.ticketGenerate.dto.ActivateTicketGenerateDto;
import com.twendeno.msauth.ticketGenerate.dto.CreateTicketGenerateDto;
import com.twendeno.msauth.ticketGenerate.entity.TicketGenerate;
import com.twendeno.msauth.user.entity.User;
import com.twendeno.msauth.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Service
public class TicketGenerateService {

    private final TicketGenerateRepository ticketGenerateRepository;
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;

    @Transactional
    public ApiResponse<List<TicketGenerate>> save(CreateTicketGenerateDto ticketGenerateDto) {

        // Check if the user exists
        User user = this.userRepository.findByEmail(ticketGenerateDto.email()).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Check if the ticket exists
        Ticket ticket = this.ticketRepository.findByName(ticketGenerateDto.ticketName()).orElseThrow(() -> new RuntimeException("Ticket not found"));

        // Generate ticket reference
        String reference = Utils.generateTicketReference();

        List<TicketGenerate> ticketGenerates = new ArrayList<>();

        for (int i = 0; i < ticketGenerateDto.count(); i++) {
            TicketGenerate ticketGenerate = TicketGenerate.builder()
                    .user(user)
                    .ticket(ticket)
                    .reference(reference)
                    .build();

            ticketGenerates.add(ticketGenerate);
        }

        List<TicketGenerate> ticketGenerates1 = ticketGenerateRepository.saveAll(ticketGenerates);

        return ApiResponse.<List<TicketGenerate>>builder()
                .data(ticketGenerates1)
                .message("Ticket generated successfully")
                .status(HttpStatus.CREATED.getReasonPhrase())
                .code(HttpStatus.CREATED.value())
                .build();

    }

    public ApiResponse<List<TicketGenerate>> getTicketGenerate() {
        List<TicketGenerate> ticketGenerates =  this.ticketGenerateRepository.findAll();
        return ApiResponse.<List<TicketGenerate>>builder()
                .data(ticketGenerates)
                .message("Ticket fetched successfully")
                .status(HttpStatus.OK.getReasonPhrase())
                .code(HttpStatus.OK.value())
                .build();
    }

    public ApiResponse<List<TicketGenerate>> getTicketGeneratesByRef(String reference) {
        List<TicketGenerate> ticketGenerates = this.ticketGenerateRepository.findManyByReference(reference).orElseThrow(() -> new EntityNotFoundException("Ticket not found"));
        return ApiResponse.<List<TicketGenerate>>builder()
                .data(ticketGenerates)
                .message("Ticket fetched successfully")
                .status(HttpStatus.OK.getReasonPhrase())
                .code(HttpStatus.OK.value())
                .build();
    }

    public ApiResponse<List<TicketGenerate>> getTicketsGenerateByRef(String reference, boolean expired) {
        List<TicketGenerate> ticketGenerates = this.ticketGenerateRepository.findManyByReferenceAndActiveAndExpire(reference,expired).orElseThrow(() -> new EntityNotFoundException("Ticket not found"));
        return ApiResponse.<List<TicketGenerate>>builder()
                .data(ticketGenerates)
                .message("Ticket fetched successfully")
                .status(HttpStatus.OK.getReasonPhrase())
                .code(HttpStatus.OK.value())
                .build();
    }

    public ApiResponse<TicketGenerate> activateTicket(ActivateTicketGenerateDto activateTicketGenerateDto) {
        List<TicketGenerate> ticketGenerates = this.getTicketGeneratesByRef(activateTicketGenerateDto.reference()).getData();

        // Set last activated ticket
        TicketGenerate lastActivatedTicket  = null;

        // If  list of ticket generate is empty exit to function
        if (ticketGenerates.isEmpty()){
            return  ApiResponse.<TicketGenerate>builder()
                    .data(lastActivatedTicket)
                    .message("Ticket not found")
                    .status(HttpStatus.NOT_FOUND.getReasonPhrase())
                    .code(HttpStatus.NOT_FOUND.value())
                    .build();
        }

        for (TicketGenerate ticketGenerateItem : ticketGenerates) {
            if (ticketGenerateItem.isActive() && !ticketGenerateItem.isExpired()) {
                lastActivatedTicket = ticketGenerateItem;
                break;
            }
        }

        if (lastActivatedTicket == null) {
            // Get first element of array if list of ticket is not empty
            lastActivatedTicket = ticketGenerates.get(0);
        }

         lastActivatedTicket = setActivateTicket(lastActivatedTicket).getData();

        return ApiResponse.<TicketGenerate>builder()
                .data(lastActivatedTicket)
                .message("Ticket activated successfully")
                .status(HttpStatus.OK.getReasonPhrase())
                .code(HttpStatus.OK.value())
                .build();

    }

    private ApiResponse<TicketGenerate> setActivateTicket(TicketGenerate ticketGenerate){

        if (checkTicketGenerateExpired(ticketGenerate)){
            ticketGenerate.setExpired(true);
            ticketGenerate = ticketGenerateRepository.save(ticketGenerate);
            return  ApiResponse.<TicketGenerate>builder()
                    .data(ticketGenerate)
                    .message("Ticket expired")
                    .status(HttpStatus.OK.getReasonPhrase())
                    .code(HttpStatus.OK.value())
                    .build();
        }

        // set ticket generated as active
        ticketGenerate.setActive(true);

        // set creation
        Instant creation = Instant.now();
        ticketGenerate.setActivation(creation);

        // set expiration
        LocalDateTime localDateTime = LocalDateTime.ofInstant(creation, ZoneId.systemDefault());
        localDateTime = localDateTime.plusHours(ticketGenerate.getTicket().getDuration());

        Instant expiration = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        ticketGenerate.setExpiration(expiration);

        ticketGenerate = ticketGenerateRepository.save(ticketGenerate);

        return ApiResponse.<TicketGenerate>builder()
                .data(ticketGenerate)
                .message("Ticket activated successfully")
                .status(HttpStatus.OK.getReasonPhrase())
                .code(HttpStatus.OK.value())
                .build();
    }

    private boolean checkTicketGenerateExpired(TicketGenerate ticketGenerate){

        if (ticketGenerate.getExpiration() == null){
            return false;
        }

        Date expirationDate = Date.from(ticketGenerate.getExpiration());
        return expirationDate.before(new Date());
    }

}
