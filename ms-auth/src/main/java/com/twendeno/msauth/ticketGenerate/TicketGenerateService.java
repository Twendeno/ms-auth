package com.twendeno.msauth.ticketGenerate;

import com.twendeno.msauth.shared.Utils;
import com.twendeno.msauth.ticket.TicketRepository;
import com.twendeno.msauth.ticket.entity.Ticket;
import com.twendeno.msauth.ticketGenerate.dto.ActivateTicketGenerateDto;
import com.twendeno.msauth.ticketGenerate.dto.CreateTicketGenerateDto;
import com.twendeno.msauth.ticketGenerate.entity.TicketGenerate;
import com.twendeno.msauth.user.entity.User;
import com.twendeno.msauth.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
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
    public void save(CreateTicketGenerateDto ticketGenerateDto) {

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

    }

    public List<TicketGenerate> getTicketGenerate() {
        return this.ticketGenerateRepository.findAll();
    }

    public List<TicketGenerate> getTicketGeneratesByRef(String reference) {
        return this.ticketGenerateRepository.findManyByReference(reference).orElseThrow(() -> new RuntimeException("Ticket not found"));
    }

    public List<TicketGenerate> getTicketsGenerateByRef(String reference, boolean expired) {
        return this.ticketGenerateRepository.findManyByReferenceAndActiveAndExpire(reference,expired).orElseThrow(() -> new RuntimeException("Ticket not found"));
    }

    public TicketGenerate activateTicket(ActivateTicketGenerateDto activateTicketGenerateDto) {
        List<TicketGenerate> ticketGenerates = this.getTicketGeneratesByRef(activateTicketGenerateDto.reference());

        // Set last activated ticket
        TicketGenerate lastActivatedTicket  = null;

        // If  list of ticket generate is empty exit to function
        if (ticketGenerates.isEmpty()){
            return lastActivatedTicket;
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

        return setActivateTicket(lastActivatedTicket);

    }

    private TicketGenerate setActivateTicket(TicketGenerate ticketGenerate){

        if (checkTicketGenerateExpired(ticketGenerate)){
            ticketGenerate.setExpired(true);
            return ticketGenerateRepository.save(ticketGenerate);
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

        return ticketGenerateRepository.save(ticketGenerate);
    }

    private boolean checkTicketGenerateExpired(TicketGenerate ticketGenerate){

        if (ticketGenerate.getExpiration() == null){
            return false;
        }

        Date expirationDate = Date.from(ticketGenerate.getExpiration());
        return expirationDate.before(new Date());
    }

}
