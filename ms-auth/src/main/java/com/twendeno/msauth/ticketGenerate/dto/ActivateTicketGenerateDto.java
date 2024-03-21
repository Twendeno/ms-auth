package com.twendeno.msauth.ticketGenerate.dto;

public record ActivateTicketGenerateDto(String reference,String uuid) {
    @Override
    public String reference() {
        return reference.trim();
    }
}
